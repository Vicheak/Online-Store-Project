package com.vicheak.onlinestore.api.product;

import com.vicheak.onlinestore.api.product.web.CategoryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryDto(CategoryDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCategory(@MappingTarget Category category, CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtoList(List<Category> categories);
}
