package ru.vtb.clientrestmicroservice.dto.moneyApi;

import lombok.Data;

import java.util.Date;

@Data
public abstract class ApiWalletHistoryAbs {
    protected Long blockNumber;
    protected Date timeStamp;
    protected String contractAddress;
    protected String from;
    protected String to;
    protected String tokenName;
    protected String tokenSymbol;
    protected Long gasUsed;
    protected Long confirmations;
}
