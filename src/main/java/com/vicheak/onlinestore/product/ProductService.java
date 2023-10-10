package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CreateProductDto;
import com.vicheak.onlinestore.product.web.ProductDto;
import com.vicheak.onlinestore.product.web.UpdateProductDto;

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
