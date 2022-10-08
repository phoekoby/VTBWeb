package ru.vtb.serverrpcmicroservice.mapper.update;

import org.springframework.stereotype.Component;
import ru.vtb.serverrpcmicroservice.dto.update.UpdateProductDTO;
import ru.vtb.serverrpcmicroservice.entity.Cost;
import ru.vtb.serverrpcmicroservice.entity.Picture;
import ru.vtb.serverrpcmicroservice.entity.Product;
import ru.vtb.serverrpcmicroservice.mapper.EntityMapper;

import javax.persistence.Entity;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UpdateProductMapper  {

    public Product updateProduct(Product product, UpdateProductDTO updateProductDTO){
        Cost cost = product.getCost();
        cost.setMatic(updateProductDTO.getMatic());
        cost.setNft(updateProductDTO.getNft());
        cost.setRuble(updateProductDTO.getRubles());

        product.setName(updateProductDTO.getName());
        product.setDescription(updateProductDTO.getDescription());

        product.getCategories().clear();
//        product.getCategories().addAll(/*updateProductDTO.getCategories()...*/);
        product.getPictures().clear();
        product.getPictures().addAll(updateProductDTO.getPicturesUrl().stream().map(s -> {
            Picture picture = new Picture();
            picture.setUrl(s);
            picture.setProducts(Collections.singleton(product));
            return picture;
        }).collect(Collectors.toList()));
        return product;
    }
}
