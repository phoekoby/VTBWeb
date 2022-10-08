package ru.vtb.clientrestmicroservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionMessageEventDto {
    private Long transactionId;
    private String hash;
}
