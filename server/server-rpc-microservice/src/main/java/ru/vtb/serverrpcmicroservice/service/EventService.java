package ru.vtb.serverrpcmicroservice.service;

import ru.vtb.integrationmodule.entity.Purchase;
import ru.vtb.integrationmodule.entity.Transaction;

public interface EventService {
    void doPurchase(Purchase purchase);
    void doTransaction(Transaction transaction);
}
