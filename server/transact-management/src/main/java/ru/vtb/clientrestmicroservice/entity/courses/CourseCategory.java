package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CourseCategory extends BaseEntity {

}
