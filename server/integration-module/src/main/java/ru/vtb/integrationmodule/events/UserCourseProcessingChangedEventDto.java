package ru.vtb.integrationmodule.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseProcessingChangedEventDto implements Serializable {
    private Long userCourseId;
    private Long userId;
}
