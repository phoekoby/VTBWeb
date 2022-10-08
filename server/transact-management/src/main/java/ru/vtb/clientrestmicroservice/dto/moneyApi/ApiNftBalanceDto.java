package ru.vtb.clientrestmicroservice.dto.moneyApi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ApiNftBalanceDto {
    private String URI;
    private List<Integer> tokens;
}
