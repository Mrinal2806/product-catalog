package com.example.productcatalog.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel{
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Category category;


}
