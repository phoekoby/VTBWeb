package ru.vtb.clientrestmicroservice.dto.outputmessages;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.enumiration.Currency;

@Data
@Builder
@AllArgsConstructor
public class ExchangeCompletedEventDto {
    private Long userId;
    private Double amount;
    private Currency fromCurrency;
    private Currency toCurrency;
}
