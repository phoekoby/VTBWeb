package ru.vtb.clientrestmicroservice.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OutNftDto {
    private String url;
    private List<Integer> tokens;
}
