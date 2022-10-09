package ru.vtb.serverrpcmicroservice.dto.creation;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDTO implements Serializable {
    String name;
}
