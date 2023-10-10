package com.vicheak.onlinestore.product.web;

public record ProductDto(String name,
                         String description,
                         String image,
                         Integer categoryId) {
}
