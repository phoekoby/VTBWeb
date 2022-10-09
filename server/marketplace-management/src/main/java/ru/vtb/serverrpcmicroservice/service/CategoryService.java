package ru.vtb.serverrpcmicroservice.service;

import ru.vtb.serverrpcmicroservice.entity.Category;
import ru.vtb.serverrpcmicroservice.dto.creation.CreateCategoryDTO;
import ru.vtb.serverrpcmicroservice.dto.response.ResponseCategoryDTO;

import java.util.List;

public interface CategoryService {

    Category findCategoryById(Long id);

    ResponseCategoryDTO createCategory(CreateCategoryDTO createCategoryDTO);

    List<ResponseCategoryDTO> getAllCategories();

    ResponseCategoryDTO getCategoryById(Long id);
}
