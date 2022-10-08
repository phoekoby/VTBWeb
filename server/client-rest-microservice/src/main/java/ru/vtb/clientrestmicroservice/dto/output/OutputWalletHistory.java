package ru.vtb.clientrestmicroservice.dto.output;

import lombok.Data;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiWalletHistory;

import java.util.List;

@Data
public class OutputWalletHistory {
    private Long walletId;
    private List<ApiWalletHistory> input;
    private List<ApiWalletHistory> output;
    private List<String> processing;
}
