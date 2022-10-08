package ru.vtb.clientrestmicroservice.dto.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OutputWalletDto {
    private Long id;
    private Double rubles;
    private Double matic;
    private List<OutNftDto> nfts;
}
