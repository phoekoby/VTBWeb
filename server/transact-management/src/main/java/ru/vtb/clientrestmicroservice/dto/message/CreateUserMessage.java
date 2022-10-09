package ru.vtb.clientrestmicroservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class CreateUserMessage implements Serializable {
    private Long userId;
}
