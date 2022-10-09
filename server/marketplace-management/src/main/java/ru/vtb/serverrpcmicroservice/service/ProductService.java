package ru.vtb.serverrpcmicroservice.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.serverrpcmicroservice.dto.BuyProductDto;
import ru.vtb.serverrpcmicroservice.dto.OutTransactionDto;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateProductDTO;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseProductDTO;
import ru.vtb.serverrpcmicroservice.dto.update.UpdateProductDTO;

import java.util.List;

public interface ProductService {

    ResponseProductDTO createProduct(CreateProductDTO createProductDTO);

    List<ResponseProductDTO> getAllProducts(Pageable pageable);

    ResponseProductDTO getProductById(Long id);

    ResponseProductDTO updateProduct(UpdateProductDTO updateProductDTO);

    OutTransactionDto buyProduct(BuyProductDto buyProductDto);
}
