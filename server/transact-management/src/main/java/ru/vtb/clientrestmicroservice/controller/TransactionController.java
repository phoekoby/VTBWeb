package ru.vtb.clientrestmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<OutTransferDto> makeTransfer(@RequestBody TransferDto transferDto){
        return ResponseEntity.ok(transactionService.doTransfer(transferDto));
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
