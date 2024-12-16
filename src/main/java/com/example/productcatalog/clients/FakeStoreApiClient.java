package com.example.productcatalog.clients;

import com.example.productcatalog.dtos.FakeStoreProductDto;
import com.example.productcatalog.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {

    private final String productApiUrl = "http://fakestoreapi.com/products";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductById(Long productId) {
        ResponseEntity<FakeStoreProductDto>  fakeStoreProductDtoResponseEntity = requestForEntity(productApiUrl +"/{productId}", HttpMethod.GET, null,FakeStoreProductDto.class, productId);
        return validateFakeStoreResponse(fakeStoreProductDtoResponseEntity);
    };

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private FakeStoreProductDto validateFakeStoreResponse(ResponseEntity<FakeStoreProductDto>  fakeStoreProductDtoResponseEntity) {
        if(fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreProductDtoResponseEntity.getBody() != null) {
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }
}
