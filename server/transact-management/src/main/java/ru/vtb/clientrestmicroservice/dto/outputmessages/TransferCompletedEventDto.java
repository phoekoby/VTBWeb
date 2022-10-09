package ru.vtb.clientrestmicroservice.dto.outputmessages;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransferCompletedEventDto {
    private Long fromUserId;
    private Long toUserId;
}
