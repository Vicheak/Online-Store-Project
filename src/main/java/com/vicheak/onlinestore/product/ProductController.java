package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CreateProductDto;
import com.vicheak.onlinestore.product.web.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

     private final ProductService productService;

     @ResponseStatus(HttpStatus.CREATED)
     @PostMapping
     public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
          productService.createNew(createProductDto);
     }

     @GetMapping
     public List<ProductDto> findAll(){
          return productService.findAll();
     }

}
