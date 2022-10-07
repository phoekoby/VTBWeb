package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.integrationmodule.entity.*;
import ru.vtb.integrationmodule.entity.user.User;
import ru.vtb.integrationmodule.events.TransactionStatusChangedEventDto;
import ru.vtb.integrationmodule.events.TransactionType;
import ru.vtb.integrationmodule.events.WalletBalanceChangedEventDto;
import ru.vtb.integrationmodule.repo.PurchaseRepository;
import ru.vtb.integrationmodule.repo.TransactionRepository;
import ru.vtb.integrationmodule.repo.UserRepository;
import ru.vtb.serverrpcmicroservice.service.EventRabbitService;
import ru.vtb.serverrpcmicroservice.service.EventService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final TransactionRepository transactionRepository;
    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;
    private final EventRabbitService eventRabbitService;

    @Override
    public void doPurchase(Long purchaseId) {
        Optional<Purchase> byId = purchaseRepository.findById(purchaseId);
        if(byId.isPresent()){
            Purchase purchase = new Purchase();
            if(purchase.getTransactionStatus().equals(TransactionStatus.PROCESSING)){
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
    public void doTransaction(Long transactionId) {
        Optional<Transaction> byId = transactionRepository.findById(transactionId);
        if(byId.isPresent()){
            Transaction transaction = byId.get();
            if(transaction.getTransactionStatus().equals(TransactionStatus.PROCESSING)){
                User fromUser = transaction.getFromUser();
                User toUser = transaction.getToUser();
                Wallet fromUserWallet = fromUser.getWallet();
                Wallet toUserWallet = toUser.getWallet();
                Double needForTransaction = transaction.getAmount();
                Double fromUserHas = fromUserWallet.getAmount();
                if(fromUserHas >= needForTransaction){
                    changeBalance(fromUserWallet, -needForTransaction, TransactionType.TRANSACTION);
                    changeBalance(toUserWallet, needForTransaction, TransactionType.TRANSACTION);
                    transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                    transactionRepository.save(transaction);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.COMPLETED)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Перевод успешно выполнен")
                            .transactionType(TransactionType.TRANSACTION)
                            .transactionId(transactionId).build());
                }else{
                    transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                    transactionRepository.save(transaction);
                    eventRabbitService.sendTransactionStatusChanged(TransactionStatusChangedEventDto.builder()
                            .currStatus(TransactionStatus.ERROR)
                            .prevStatus(TransactionStatus.PROCESSING)
                            .reason("Недостаточно средств для перевода")
                            .transactionType(TransactionType.TRANSACTION)
                            .transactionId(transactionId).build());
                }
            }
        }
    }

    @Override
    public void doExchange(Long exchangeId) {
        //ждем api от хакатона
    }

    @Override
    public void analyseUserCourseProcessingChanged(Long userCourseId) {
        //подумать как тут делать
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
