package com.vicheak.onlinestore.api.product;

import com.vicheak.onlinestore.api.product.web.ProductDto;
import com.vicheak.onlinestore.api.product.web.UpdateProductDto;
import com.vicheak.onlinestore.api.product.web.CreateProductDto;
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

     @ResponseStatus(HttpStatus.NO_CONTENT)
     @DeleteMapping("/{uuid}")
     public void deleteByUuid(@PathVariable("uuid") String uuid){
          productService.deleteByUuid(uuid);
     }

     @ResponseStatus(HttpStatus.OK)
     @PatchMapping("/{uuid}")
     public void updateByUuid(@PathVariable("uuid") String uuid,
                              @RequestBody UpdateProductDto updateProductDto){
          productService.updateByUuid(uuid, updateProductDto);
     }

     @ResponseStatus(HttpStatus.CREATED)
     @PostMapping
     public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
          productService.createNew(createProductDto);
     }

     @GetMapping
     public List<ProductDto> findAll(){
          return productService.findAll();
     }

     @GetMapping("/{uuid}")
     public ProductDto findByUuid(@PathVariable("uuid") String uuid){
          return productService.findByUuid(uuid);
     }

}
