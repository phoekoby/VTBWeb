package ru.vtb.clientrestmicroservice.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OutTransactionDto {
    private String hash;
    private Long id;
}
