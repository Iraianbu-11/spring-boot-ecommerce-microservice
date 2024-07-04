package com.iraianbu.ecommerce.service;

import com.iraianbu.ecommerce.body.CustomerResponse;
import com.iraianbu.ecommerce.controller.CustomerRequest;
import com.iraianbu.ecommerce.model.Customer;
import com.iraianbu.ecommerce.repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    @CachePut(value = "customer", key = "#request.id")
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    @CacheEvict(value = "customer", key = "#customerRequest.id",allEntries = true)
    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        mergeCustomer(customer, customerRequest);
        customerRepository.save(customer);
        log.info("Updated Customer {}", customerRequest.id());
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    @Cacheable(value = "customer")
    public List<Customer> getAllCustomers() {
        log.info("getAllCustomers method called");
        return customerRepository.findAll();
    }

    public Boolean getCustomerById(String id) {
        log.info("getCustomerById method called for ID: {}", id);
        return customerRepository.findById(id).isPresent();
    }

    @CacheEvict(value = "customer", key = "#id",allEntries = true)
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Cacheable(value = "customer", key = "#customerId")
    public CustomerResponse findById(String customerId) {
        log.info("findById method called for ID: {}", customerId);
        return customerRepository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new RuntimeException(String.format("No customer found with the provided ID: %s", customerId)));
    }
}
