package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.integrationmodule.repo.TransactionRepository;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {
    private final TransactionRepository transactionRepository;

}
