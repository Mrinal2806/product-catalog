package com.example.productcatalog.services;

import com.example.productcatalog.dtos.FakeStoreProductDto;
import com.example.productcatalog.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);

    Product createProduct(FakeStoreProductDto fakeStoreProductDto);

    List<Product> getAllProducts();

    Product updateProduct(Long productId, Product request);
}
