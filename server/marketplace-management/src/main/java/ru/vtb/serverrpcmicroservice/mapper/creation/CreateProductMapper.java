package ru.vtb.serverrpcmicroservice.mapper.creation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateProductDTO;
import ru.vtb.serverrpcmicroservice.entity.Category;
import ru.vtb.serverrpcmicroservice.entity.Cost;
import ru.vtb.serverrpcmicroservice.entity.Picture;
import ru.vtb.serverrpcmicroservice.entity.Product;
import ru.vtb.serverrpcmicroservice.mapper.EntityMapper;
import ru.vtb.serverrpcmicroservice.service.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateProductMapper implements EntityMapper<Product, CreateProductDTO> {
    private final CategoryService categoryService;
    @Override
    public Product toEntity(CreateProductDTO createProductDTO) {
        Product product = new Product();
        product.setName(createProductDTO.getName());
        product.setDescription(createProductDTO.getDescription());
        product.setPictures(createProductDTO.getPicturesUrl().stream().map(s -> {
            Picture picture = new Picture();
            picture.setUrl(s);
            picture.setProducts(Collections.singleton(product));
            return picture;
        }).collect(Collectors.toList()));
        product.setCategories(createProductDTO
                .getCategories()
                .stream()
                .map(categoryService::findCategoryById)
                .collect(Collectors.toList()));
        product.setCost(new Cost(createProductDTO.getRubles(), createProductDTO.getMatic(), createProductDTO.getNft()));
        return null;
    }

    @Override
    public CreateProductDTO toDto(Product product) {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setDescription(product.getDescription());
        createProductDTO.setCategories(product
                .getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList()));
        createProductDTO.setPicturesUrl(product
                .getPictures()
                .stream()
                .map(Picture::getUrl)
                .collect(Collectors.toList()));
        createProductDTO.setRubles(product.getCost().getRuble());
        createProductDTO.setMatic(product.getCost().getMatic());
        createProductDTO.setNft(product.getCost().getNft());
        return createProductDTO;
    }

    @Override
    public List<Product> toEntity(List<CreateProductDTO> createProductDTOS) {
        return createProductDTOS.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<CreateProductDTO> toDto(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }
}
