package ru.vtb.serverrpcmicroservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCategoryDTO implements Serializable {
    private Long id;
    private String name;
    private Date createDate;
    private Date updateDate;
}
