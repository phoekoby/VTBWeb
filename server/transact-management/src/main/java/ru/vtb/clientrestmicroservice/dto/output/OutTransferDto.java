package ru.vtb.clientrestmicroservice.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OutTransferDto {
    private Long id;
    private String hash;
}
