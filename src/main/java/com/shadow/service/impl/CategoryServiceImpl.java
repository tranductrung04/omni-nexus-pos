package com.shadow.service.impl;

import com.shadow.domain.UserRole;
import com.shadow.exception.CategoryException;
import com.shadow.exception.UserException;
import com.shadow.mapper.CategoryMapper;
import com.shadow.model.Category;
import com.shadow.model.Store;
import com.shadow.model.User;
import com.shadow.payload.dto.CategoryDTO;
import com.shadow.repository.CategoryRepository;
import com.shadow.service.CategoryService;
import com.shadow.service.StoreService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final StoreService storeService;
    private final UserService userService;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryException, UserException {
        User currentUser = userService.getCurrentUser();

        Store store = storeService.getStoreEntityById(categoryDTO.getStoreId());

        Category category = Category.builder()
                .name(categoryDTO.getName())
                .store(store)
                .build();

        checkAuthority(currentUser, store);

        Category savedcategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedcategory);
    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws CategoryException, UserException {
        User currentUser = userService.getCurrentUser();

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryException("Category not found")
        );

        checkAuthority(currentUser, category.getStore());

        category.setName(categoryDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryException, UserException {
        User currentUser = userService.getCurrentUser();

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryException("Category not found")
        );

        checkAuthority(currentUser, category.getStore());

        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryEntityById(Long id) throws CategoryException {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryException("Category not found")
        );

        return category;
    }

    public void checkAuthority(User user, Store store) throws CategoryException {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new CategoryException("You don't have permission to manage this category");
        }
    }
}
