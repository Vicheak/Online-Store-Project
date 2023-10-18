package com.vicheak.onlinestore.api.product;

import com.vicheak.onlinestore.api.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createNew(CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void updateByName(String name, CategoryDto categoryDto) {
        //load category by passed name
        Category category = categoryRepository.findByName(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Category with name = %s not found!", name))
                );

        //map from dto to entity
        categoryMapper.toCategory(category, categoryDto);

        //save category resource which has been updated
        categoryRepository.save(category);
    }

    @Override
    public void deleteByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Category with name = %s not found!", name))
                );

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Category with name = %s not found!", name))
                );
        return categoryMapper.toCategoryDto(category);
    }
}
