package ru.vtb.clientrestmicroservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TransactionServiceImpl {
    private final RestTemplate restTemplate;
    private String baseUrl;

}
