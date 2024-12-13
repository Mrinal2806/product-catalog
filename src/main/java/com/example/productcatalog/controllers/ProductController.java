package com.example.productcatalog.controllers;
import com.example.productcatalog.dtos.CategoryDto;
import com.example.productcatalog.dtos.ProductDto;
import com.example.productcatalog.models.Product;
import com.example.productcatalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public List<Product> getAllProducts() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Iphone");
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    };

    @GetMapping("/{productId}")
    public ProductDto findProductById(@PathVariable Long productId) {
        //        Product product = new Product();
        //        product.setId(productId);
        //        return product;
        Product product = productService.getProductById(productId);
        if (product == null) return null;
        return from(product);

    };

    @PostMapping("")
    public Product createProduct(@RequestBody Product product) {
        return product;
    }


    private ProductDto from (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

}
