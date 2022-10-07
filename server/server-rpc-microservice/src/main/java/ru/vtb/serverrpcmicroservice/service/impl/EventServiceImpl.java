package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.integrationmodule.entity.*;
import ru.vtb.integrationmodule.entity.user.User;
import ru.vtb.integrationmodule.events.TransactionStatusChangedEventDto;
import ru.vtb.integrationmodule.entity.TransactionType;
import ru.vtb.integrationmodule.events.UserCourseProcessingChangedEventDto;
import ru.vtb.integrationmodule.events.WalletBalanceChangedEventDto;
import ru.vtb.integrationmodule.repo.PurchaseRepository;
import ru.vtb.integrationmodule.repo.TransferRepository;
import ru.vtb.integrationmodule.repo.UserRepository;
import ru.vtb.serverrpcmicroservice.service.EventRabbitService;
import ru.vtb.serverrpcmicroservice.service.EventService;

import java.util.Optional;

import static ru.vtb.integrationmodule.entity.courses.CourseStatus.COMPLETED;
import static ru.vtb.integrationmodule.entity.courses.CourseStatus.PROCESSING;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final TransferRepository transferRepository;
    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;
    private final EventRabbitService eventRabbitService;

    @Override
    public void doPurchase(Long purchaseId) {
        Optional<Purchase> byId = purchaseRepository.findById(purchaseId);
        if(byId.isPresent()){
            Purchase purchase = new Purchase();
            if(TransactionStatus.PROCESSING.equals(purchase.getTransactionStatus())){
                Product product = purchase.getProduct();
                Wallet buyerUserWallet = purchase.getBuyerUser().getWallet();
                Wallet productOwnerWallet = product.getOwner().getWallet();
                Double buyerHas = purchase.getBuyerUser().getWallet().getAmount();
                Double productCost = product.getCost();
                if(buyerHas >= productCost){
                    purchase.setPrevOwnerUser(product.getOwner());
                    product.setOwner(purchase.getBuyerUser());
                    //поменяли кол-во у покупателя и добавили сущности для истории
                    changeBalance(buyerUserWallet, -productCost, TransactionType.PURCHASE);
                    //поменяли кол-вл у продавца и добавили сущность для истории
                    changeBalance(productOwnerWallet, productCost, TransactionType.PURCHASE);
                    //пометили как завершенную и отправили уведомление клиенту
                    purchase.setTransactionStatus(TransactionStatus.COMPLETED);
                    purchaseRepository.save(purchase);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.COMPLETED)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Покупка успешно выполнена")
                            .transactionType(TransactionType.PURCHASE)
                            .transactionId(purchaseId).build());
                }else{
                    //пометили как ошибочный и отправили уведомление клиенту
                    purchase.setTransactionStatus(TransactionStatus.ERROR);
                    purchaseRepository.save(purchase);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.ERROR)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Недостаточно средств для покупки")
                            .transactionType(TransactionType.PURCHASE)
                            .transactionId(purchaseId).build());
                }
            }
        }
    }

    @Override
    public void doTransfer(Long transferId) {
        Optional<Transfer> byId = transferRepository.findById(transferId);
        if(byId.isPresent()){
            Transfer transfer = byId.get();
            if(TransactionStatus.PROCESSING.equals(transfer.getTransactionStatus())){
                User fromUser = transfer.getFromUser();
                User toUser = transfer.getToUser();
                Wallet fromUserWallet = fromUser.getWallet();
                Wallet toUserWallet = toUser.getWallet();
                Double needForTransaction = transfer.getAmount();
                Double fromUserHas = fromUserWallet.getAmount();
                if(fromUserHas >= needForTransaction){
                    changeBalance(fromUserWallet, -needForTransaction, TransactionType.TRANSFER);
                    changeBalance(toUserWallet, needForTransaction, TransactionType.TRANSFER);
                    transfer.setTransactionStatus(TransactionStatus.COMPLETED);
                    transferRepository.save(transfer);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.COMPLETED)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Перевод успешно выполнен")
                            .transactionType(TransactionType.TRANSFER)
                            .transactionId(transferId).build());
                }else{
                    transfer.setTransactionStatus(TransactionStatus.COMPLETED);
                    transferRepository.save(transfer);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.ERROR)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Недостаточно средств для перевода")
                            .transactionType(TransactionType.TRANSFER)
                            .transactionId(transferId).build());
                }
            }
        }
    }

    @Override
    public void doExchange(Long exchangeId) {
        //ждем api от хакатона
    }

    @Override
    public void analyseUserCourseProcessingChanged(UserCourseProcessingChangedEventDto userCourseProcessingChangedEventDto) {
        if(null == userCourseProcessingChangedEventDto.getPrevStatus() &&PROCESSING.equals(userCourseProcessingChangedEventDto.getCurrStatus())){
            //просто отправить поздравления или что то в этом духе
        }else if(PROCESSING.equals(userCourseProcessingChangedEventDto.getPrevStatus()) && COMPLETED.equals(userCourseProcessingChangedEventDto.getCurrStatus())){
            //начислить на счет, причем чем другую транзакцию, т.е закинуть себе в очередь
        }else{
            //?
        }
    }


    private void changeBalance(Wallet wallet, Double amount, TransactionType transactionType){
        wallet.setAmount(wallet.getAmount() + amount);
        WalletBalanceChanged walletBalanceChanged = new WalletBalanceChanged();
        walletBalanceChanged.setWallet(wallet);
        walletBalanceChanged.setChangedAmount(amount);
        eventRabbitService.sendWalletBalanceChanged(WalletBalanceChangedEventDto.builder()
                .changeAmount(amount)
                .walletId(wallet.getId())
                .transactionType(transactionType)
                .build());
    }
}
