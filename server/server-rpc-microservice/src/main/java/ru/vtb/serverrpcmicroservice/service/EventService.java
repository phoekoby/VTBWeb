package ru.vtb.serverrpcmicroservice.service;

import ru.vtb.integrationmodule.entity.Exchange;
import ru.vtb.integrationmodule.entity.Purchase;
import ru.vtb.integrationmodule.entity.Transaction;
import ru.vtb.integrationmodule.entity.courses.UserCourse;

public interface EventService {
    void doPurchase(Purchase purchase);
    void doTransaction(Transaction transaction);
    void doExchange(Exchange exchange);
    void analyseUserCourseProcessingChanged(UserCourse userCourse);
}
