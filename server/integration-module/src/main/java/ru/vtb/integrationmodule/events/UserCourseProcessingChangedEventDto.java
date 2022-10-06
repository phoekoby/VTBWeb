package ru.vtb.integrationmodule.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.integrationmodule.entity.courses.CourseStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseProcessingChangedEventDto implements Serializable {
    private Long userCourseId;
    private Long userId;
    private CourseStatus prevStatus;
    private CourseStatus currStatus;
}
