package ru.vtb.serverrpcmicroservice.dto.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private List<Long> categories;
    private List<String> picturesUrl;
    private Double rubles;
    private Double matic;
    private Integer nft;
}
