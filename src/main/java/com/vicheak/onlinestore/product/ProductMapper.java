package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CreateProductDto;
import com.vicheak.onlinestore.product.web.ProductDto;
import com.vicheak.onlinestore.product.web.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product fromCreateProductDto(CreateProductDto dto);
    @Mapping(source = "categoryId", target = "category.id")
    Product fromUpdateProductDto(UpdateProductDto dto);
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toProductDto(Product product);
    List<ProductDto> toProductDtoList(List<Product> products);
}
