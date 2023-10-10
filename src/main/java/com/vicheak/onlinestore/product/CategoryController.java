package com.vicheak.onlinestore.product;

import com.vicheak.onlinestore.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{catName}")
    public CategoryDto findByName(@PathVariable String catName) {
        return categoryService.findByName(catName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody CategoryDto categoryDto) {
        categoryService.createNew(categoryDto);
    }
}
