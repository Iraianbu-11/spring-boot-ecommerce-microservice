package com.iraianbu.orderservice.product;


import com.iraianbu.orderservice.body.PurchaseResponse;
import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.iraianbu.orderservice.body.PurchaseRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.customer-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody,headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl+"/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );

        if(responseEntity.getStatusCode().isError()){
            throw new RuntimeException("An error occur while processing the products purchase " + responseEntity.getStatusCode());
        }
    return responseEntity.getBody();
    }
}
