package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.serverrpcmicroservice.dto.BuyProductDto;
import ru.vtb.serverrpcmicroservice.dto.Currency;
import ru.vtb.serverrpcmicroservice.dto.OutTransactionDto;
import ru.vtb.serverrpcmicroservice.dto.PurchaseDto;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateProductDTO;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseProductDTO;
import ru.vtb.serverrpcmicroservice.dto.update.UpdateProductDTO;
import ru.vtb.serverrpcmicroservice.entity.Product;
import ru.vtb.serverrpcmicroservice.mapper.creation.CreateProductMapper;
import ru.vtb.serverrpcmicroservice.mapper.response.ResponseProductMapper;
import ru.vtb.serverrpcmicroservice.mapper.update.UpdateProductMapper;
import ru.vtb.serverrpcmicroservice.repo.ProductRepository;
import ru.vtb.serverrpcmicroservice.service.ProductService;
import ru.vtb.serverrpcmicroservice.service.TransactFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final CreateProductMapper createProductMapper;

    private final ResponseProductMapper responseProductMapper;

    private final ProductRepository productRepository;

    private final UpdateProductMapper updateProductMapper;

    private final TransactFeignClient transactFeignClient;

    private static final Long ADMIN_WALLET_ID = 1L;

    @Override
    public ResponseProductDTO createProduct(CreateProductDTO createProductDTO) {
        log.debug("Try to create Product {}", createProductDTO);
        Product product = createProductMapper.toEntity(createProductDTO);
        product = productRepository.save(product);
        return responseProductMapper.toDto(product);
    }

    @Override
    public List<ResponseProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(responseProductMapper::toDto).toList();
    }

    @Override
    public ResponseProductDTO getProductById(Long id) {
        return responseProductMapper.toDto(findProductById(id));
    }

    @Override
    public ResponseProductDTO updateProduct(UpdateProductDTO updateProductDTO) {
        Product product = findProductById(updateProductDTO.getId());
        product = updateProductMapper.updateProduct(product, updateProductDTO);
        product = productRepository.save(product);
        return responseProductMapper.toDto(product);
    }

    @Override
    public OutTransactionDto buyProduct(BuyProductDto buyProductDto) {
        Product product = findProductById(buyProductDto.getProductId());
        Double cost = null;
        if (buyProductDto.getCurrency() == Currency.NFT) {
            cost = product.getCost().getNft().doubleValue();
        } else if (buyProductDto.getCurrency() == Currency.MATIC) {
            cost = product.getCost().getMatic();
        } else if (buyProductDto.getCurrency() == Currency.RUBLE) {
            cost = product.getCost().getRuble();
        }
        PurchaseDto purchaseDto = new PurchaseDto(buyProductDto.getFromWalletId(), ADMIN_WALLET_ID, product.getId(), buyProductDto.getCurrency(), cost);
        return transactFeignClient.purchaseProduct(purchaseDto);
    }

    protected Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product with such id Not Found"));
    }
}
