package ru.vtb.serverrpcmicroservice.dto.creation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductDTO implements Serializable {

    private String name;
    private String description;
    private List<Long> categories;
    private List<String> picturesUrl;
    private Double rubles;
    private Double matic;
    private Integer nft;
}
