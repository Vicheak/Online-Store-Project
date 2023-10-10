package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CreateProductDto;
import com.vicheak.onlinestore.product.web.ProductDto;
import com.vicheak.onlinestore.product.web.UpdateProductDto;
import com.vicheak.onlinestore.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createNew(CreateProductDto createProductDto) {
        Product product = productMapper.fromCreateProductDto(createProductDto);
        product.setUuid(UUID.randomUUID().toString());
        product.setCode("PRO-" + RandomUtil.generateCode());
        productRepository.save(product);
    }

    @Override
    public void updateByUuid(String uuid, UpdateProductDto updateProductDto) {

    }

    @Override
    public void deleteByUuid(String uuid) {

    }

    @Override
    public List<ProductDto> findAll() {
        return productMapper.toProductDtoList(productRepository.findAll());
    }

    @Override
    public ProductDto findByUuid(String uuid) {
        return null;
    }
}
