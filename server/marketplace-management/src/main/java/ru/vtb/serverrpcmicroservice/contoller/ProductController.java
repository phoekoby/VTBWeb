package ru.vtb.serverrpcmicroservice.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vtb.serverrpcmicroservice.config.AuthorityConstants;
import ru.vtb.serverrpcmicroservice.dto.BuyProductDto;
import ru.vtb.serverrpcmicroservice.dto.OutTransactionDto;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateProductDTO;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseProductDTO;
import ru.vtb.serverrpcmicroservice.dto.update.UpdateProductDTO;
import ru.vtb.serverrpcmicroservice.service.ProductService;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    private final AuthorityConstants authorityConstants;

    @PreAuthorize("hasAuthority(#authorityConstants.STORE_MANAGEMENT)")
    @PostMapping("/v1/product/create")
    public ResponseEntity<ResponseProductDTO> createProduct(
            @RequestBody CreateProductDTO createProductDTO) {
        log.debug("REST request for create Product: {}", createProductDTO);
        ResponseProductDTO responseProductDTO = productService.createProduct(createProductDTO);
        return ResponseEntity.ok(responseProductDTO);
    }

    @PreAuthorize("hasAuthority(#authorityConstants.STORE_MANAGEMENT)")
    @PostMapping("/v1/product/update")
    public ResponseEntity<ResponseProductDTO> updateProduct(
            @RequestBody UpdateProductDTO updateProductDTO
    ) {
        log.debug("REST request for create Product: {}", updateProductDTO);
        ResponseProductDTO responseProductDTO = productService.updateProduct(updateProductDTO);
        return ResponseEntity.ok(responseProductDTO);
    }

    @GetMapping("/v1/products")
    public ResponseEntity<List<ResponseProductDTO>> getAllProducts(
            Pageable pageable
    ) {
        log.debug("REST Request for getting all Products");
        List<ResponseProductDTO> result = productService.getAllProducts(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/products/{id}")
    public ResponseEntity<List<ResponseProductDTO>> getProduct(
            Pageable pageable,
            @PathVariable Long id
    ) {
        log.debug("REST Request for getting product by Id {}", id);
        List<ResponseProductDTO> result = productService.getAllProducts(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/product/buy")
    public ResponseEntity<OutTransactionDto> buyProduct(
            @RequestBody BuyProductDto buyProductDto
    ) {
        log.debug("REST Request for buy product {}", buyProductDto);
        OutTransactionDto result = productService.buyProduct(buyProductDto);
        return ResponseEntity.ok(result);
    }
}
