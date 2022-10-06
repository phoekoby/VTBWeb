package ru.vtb.serverrpcmicroservice.service;

public interface EventService {
    void doPurchase(Long purchaseId);
    void doTransaction(Long transactionId);
    void doExchange(Long exchangeId);
    void analyseUserCourseProcessingChanged(Long userCourseId);
}
