package ru.vtb.clientrestmicroservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExchangeMessageEventDto {
    private Long exchangeId;
    private Long transactionFrom;
    private Long transactionTo;
}
