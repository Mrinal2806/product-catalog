package com.example.productcatalog.controllers;
import com.example.productcatalog.dtos.CategoryDto;
import com.example.productcatalog.dtos.FakeStoreProductDto;
import com.example.productcatalog.dtos.ProductDto;
import com.example.productcatalog.models.Category;
import com.example.productcatalog.models.Product;
import com.example.productcatalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public List<ProductDto> getAllProducts() {
//        Product product = new Product();
//        product.setId(2L);
//        product.setName("Iphone");
//        List<Product> products = new ArrayList<>();
//        products.add(product);
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for(Product product : products) {
            productDtos.add(from(product));
        }
        return productDtos;
    };

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long productId) {
        //        Product product = new Product();
        //        product.setId(productId);
        //        return product;

        //        HttpHeaders headers = new HttpHeaders();
        //        headers.add("Custom-Header", "HeaderValue");
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("called by", "intelligent");

            if (productId <= 0) {
                throw new IllegalArgumentException("Please enter a valid product id");
            }
            Product product = productService.getProductById(productId);
            if (product == null) return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(from(product), headers, HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            throw exception;
        }
    };

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

    @PostMapping("")
    public Product createProduct(@RequestBody Product product) {

        return product;
    }

    private FakeStoreProductDto to(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        return fakeStoreProductDto;
    };

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto request) {
        Product productRequest = from(request);
        Product product = productService.updateProduct(id, productRequest);
        return from(product);
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }







}
