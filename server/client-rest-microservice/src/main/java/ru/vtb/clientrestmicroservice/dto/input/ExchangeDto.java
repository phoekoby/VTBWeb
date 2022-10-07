package ru.vtb.clientrestmicroservice.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.clientrestmicroservice.dto.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeDto {
    private Long userId;
    private Currency from;
    private Currency to;
    private Double amount;
}
