package com.iraianbu.ecommerce.service;

import com.iraianbu.ecommerce.body.ProductPurchaseRequest;
import com.iraianbu.ecommerce.body.ProductPurchaseResponse;
import com.iraianbu.ecommerce.body.ProductRequest;
import com.iraianbu.ecommerce.body.ProductResponse;
import com.iraianbu.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    public Integer createProduct(ProductRequest productRequest) {
        log.info("{}" , productRequest);
        var product = mapper.toProduct(productRequest);
        log.info("The Product request is {}" , product);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest) {
        var productsId = productPurchaseRequest.
                stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(productsId);

        if(productsId.size() != storedProducts.size()){
            throw new RuntimeException("One or more products not exists");
        }

        var storeRequest = productPurchaseRequest
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storeRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new RuntimeException("Insufficient Stock for product with ID " + productRequest.productId());
            }

            var availableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(availableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }


        return purchasedProducts;
    }
    @Cacheable(value = "product",key = "#productId")
    public ProductResponse findById(Integer productId) {
        log.info("findById Method Called");
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
    }

    @Cacheable(value = "product")
    public List<ProductResponse> findAll() {
        log.info("findAll Method Called");
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());

    }
}
