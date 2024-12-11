package com.example.productcatalog.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel{
    private String name;
    private String description;
    private String imageUrl;


}
