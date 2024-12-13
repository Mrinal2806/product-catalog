package com.example.productcatalog.services;

import com.example.productcatalog.models.Product;

public interface IProductService {
    Product getProductById(Long productId);
}
