package ru.vtb.serverrpcmicroservice.mapper.response;

import org.springframework.stereotype.Component;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseProductDTO;
import ru.vtb.serverrpcmicroservice.entity.Category;
import ru.vtb.serverrpcmicroservice.entity.Picture;
import ru.vtb.serverrpcmicroservice.entity.Product;
import ru.vtb.serverrpcmicroservice.mapper.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ResponseProductMapper implements EntityMapper<Product, ResponseProductDTO> {
    @Override
    public Product toEntity(ResponseProductDTO responseProductDTO) {
        return null;
    }

    @Override
    public ResponseProductDTO toDto(Product product) {
        ResponseProductDTO responseProductDTO = new ResponseProductDTO();
        responseProductDTO.setId(product.getId());
        responseProductDTO.setName(product.getName());
        responseProductDTO.setDescription(product.getDescription());
        responseProductDTO.setCategories(product
                .getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList()));
        responseProductDTO.setPicturesUrl(product
                .getPictures()
                .stream()
                .map(Picture::getUrl)
                .collect(Collectors.toList()));
        responseProductDTO.setRubles(product.getCost().getRuble());
        responseProductDTO.setMatic(product.getCost().getMatic());
        responseProductDTO.setNft(product.getCost().getNft());
        return responseProductDTO;
    }

    @Override
    public List<Product> toEntity(List<ResponseProductDTO> responseProductDTOS) {
        return null;
    }

    @Override
    public List<ResponseProductDTO> toDto(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }
}
