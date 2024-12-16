package com.example.productcatalog.services;

import com.example.productcatalog.clients.FakeStoreApiClient;
import com.example.productcatalog.dtos.FakeStoreProductDto;
import com.example.productcatalog.models.Category;
import com.example.productcatalog.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    public Product getProductById(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(productId);
        return from(fakeStoreProductDto);
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<Product> products = new ArrayList<>();
        ResponseEntity<FakeStoreProductDto[]> listResponseEntity = restTemplate.getForEntity("http://fakestoreapi.com/products", FakeStoreProductDto[].class);
        if(listResponseEntity.getStatusCode().is2xxSuccessful() && listResponseEntity.getBody() != null) {
            for(FakeStoreProductDto fakeStoreProductDto : listResponseEntity.getBody()) {
                products.add(from(fakeStoreProductDto));
            }
        }
        return products;
    }

    public Product createProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<Object>  fakeStoreProductDtoResponseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products", Objects.class, fakeStoreProductDto);

//        if(fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreProductDtoResponseEntity.getBody() != null) {
//            return from(fakeStoreProductDtoResponseEntity.getBody());
//        }
        return null;
    };

    @Override
    public Product updateProduct(Long productId, Product request) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoRequest = from(request);
        FakeStoreProductDto response =
                requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.PUT,fakeStoreProductDtoRequest, FakeStoreProductDto.class,productId).getBody();

        return from(response);
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }


    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }



}
