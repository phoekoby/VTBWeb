package ru.vtb.serverrpcmicroservice.service;

import ru.vtb.integrationmodule.events.UserCourseProcessingChangedEventDto;

public interface EventService {
    void doPurchase(Long purchaseId);
    void doTransfer(Long transactionId);
    void doExchange(Long exchangeId);
    void analyseUserCourseProcessingChanged(UserCourseProcessingChangedEventDto userCourseProcessingChangedEventDto);
}
