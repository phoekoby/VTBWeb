package ru.vtb.serverrpcmicroservice.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vtb.serverrpcmicroservice.config.AuthorityConstants;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateCategoryDTO;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseCategoryDTO;
import ru.vtb.serverrpcmicroservice.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    private final AuthorityConstants authorityConstants;

    @PreAuthorize("hasAuthority(#authorityConstants.STORE_MANAGEMENT)")
    @PostMapping("/v1/category/create")
    public ResponseEntity<ResponseCategoryDTO> createCategory(
            @RequestBody CreateCategoryDTO createCategoryDTO
    ) {
        log.debug("REST Request for creating Category {}", createCategoryDTO);
        ResponseCategoryDTO result = categoryService.createCategory(createCategoryDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/categories")
    public ResponseEntity<List<ResponseCategoryDTO>> getAllCategories(
    ) {
        log.debug("REST Request for getting All Category");
        List<ResponseCategoryDTO> result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/categories/{id}")
    public ResponseEntity<ResponseCategoryDTO> getAllCategoryByid(
            @PathVariable Long id
    ) {
        log.debug("REST Request for get Category {}", id);
        ResponseCategoryDTO result = categoryService.getCategoryById(id);
        return ResponseEntity.ok(result);
    }
}
