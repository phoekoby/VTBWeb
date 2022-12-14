package ru.vtb.clientrestmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.PurchaseDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransactionDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<OutTransactionDto> makeTransfer(@RequestBody TransferDto transferDto){
        return ResponseEntity.ok(transactionService.doTransfer(transferDto));
    }

    @PostMapping("/exchange")
    public ResponseEntity<OutTransactionDto> makeExchange(@RequestBody ExchangeDto exchangeDto){
        return ResponseEntity.ok(transactionService.doExchange(exchangeDto));
    }

    @PostMapping("/purchase")
    public ResponseEntity<OutTransactionDto> makePurchase(@RequestBody PurchaseDto purchaseDto){
        return ResponseEntity.ok(transactionService.doPurchase(purchaseDto));
    }

    @GetMapping("/{hash}")
    public ResponseEntity<String> getTransactionInfo(@PathVariable String hash){
        return ResponseEntity.ok(transactionService.getStatus(hash));
    }

}
