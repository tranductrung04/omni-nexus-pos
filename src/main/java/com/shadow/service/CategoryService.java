package com.shadow.service;

import com.shadow.exception.CategoryException;
import com.shadow.exception.UserException;
import com.shadow.model.Category;
import com.shadow.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws UserException, CategoryException;

    List<CategoryDTO> getCategoriesByStore(Long storeId);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws CategoryException, UserException;

    void deleteCategory(Long id) throws UserException, CategoryException;

    Category getCategoryEntityById(Long id) throws UserException, CategoryException;
}
