package ru.vtb.serverrpcmicroservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.vtb.serverrpcmicroservice.dto.OutTransactionDto;
import ru.vtb.serverrpcmicroservice.dto.PurchaseDto;

@FeignClient(name = "transact-management")
public interface TransactFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/purchase", consumes = "application/json")
    OutTransactionDto purchaseProduct(PurchaseDto purchaseDto);
}
