package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateCategoryDTO;
import ru.vtb.serverrpcmicroservice.entity.Category;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseCategoryDTO;
import ru.vtb.serverrpcmicroservice.repo.CategoryRepository;
import ru.vtb.serverrpcmicroservice.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category with such id Not Found"));
    }

    @Override
    public ResponseCategoryDTO createCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = new Category();
        category.setName(createCategoryDTO.getName());
        category = categoryRepository.save(category);
        return mapToResponseCategoryDto(category);
    }

    @Override
    public List<ResponseCategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(this::mapToResponseCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseCategoryDTO getCategoryById(Long id) {
        return mapToResponseCategoryDto(this.findCategoryById(id));
    }

    private ResponseCategoryDTO mapToResponseCategoryDto(Category category) {
        ResponseCategoryDTO responseCategoryDTO = new ResponseCategoryDTO();
        responseCategoryDTO.setCreateDate(category.getCreateDate());
        responseCategoryDTO.setUpdateDate(category.getUpdateDate());
        responseCategoryDTO.setName(category.getName());
        responseCategoryDTO.setId(category.getId());
        return responseCategoryDTO;
    }
}
