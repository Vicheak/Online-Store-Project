package com.vicheak.onlinestore.product.web;

public record UpdateProductDto(String name,
                               String description,
                               Integer categoryId) {
}
