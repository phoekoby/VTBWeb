package ru.vtb.clientrestmicroservice.dto.moneyApi;

import lombok.Data;

@Data
public class ApiWalletNftHistory extends ApiWalletHistoryAbs{
    private Long tokenId;
}
