package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryDto(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
    List<CategoryDto> toCategoryDtoList(List<Category> categories);
}
