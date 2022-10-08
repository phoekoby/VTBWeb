package ru.vtb.clientrestmicroservice.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.clientrestmicroservice.entity.transaction.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeDto {
    private Long fromUserId;
    private Long toUserId;
    private Currency from;
    private Currency to;
    private Double amount;
}
