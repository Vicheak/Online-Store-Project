package com.vicheak.onlinestore.api.product;

import com.vicheak.onlinestore.api.product.web.ProductDto;
import com.vicheak.onlinestore.api.product.web.UpdateProductDto;
import com.vicheak.onlinestore.api.product.web.CreateProductDto;

import java.util.List;

public interface ProductService {
    /**
     *
     * @param createProductDto
     */
    void createNew(CreateProductDto createProductDto);

    /**
     *
     * @param uuid
     * @param updateProductDto
     */
    void updateByUuid(String uuid, UpdateProductDto updateProductDto);

    /**
     *
     * @param uuid
     */
    void deleteByUuid(String uuid);

    /**
     *
     * @return
     */
    List<ProductDto> findAll();

    /**
     *
     * @param uuid
     * @return
     */
    ProductDto findByUuid(String uuid);
}
