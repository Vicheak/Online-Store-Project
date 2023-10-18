package com.vicheak.onlinestore.api.product;

import com.vicheak.onlinestore.api.product.web.ProductDto;
import com.vicheak.onlinestore.api.product.web.UpdateProductDto;
import com.vicheak.onlinestore.api.product.web.CreateProductDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product fromCreateProductDto(CreateProductDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProductDto(@MappingTarget Product product, UpdateProductDto dto);
    @Mapping(source = "category.name", target = "category")
    ProductDto toProductDto(Product product);
    List<ProductDto> toProductDtoList(List<Product> products);
}
