package com.iraianbu.customerservice.service;

import com.iraianbu.customerservice.controller.CustomerRequest;
import com.iraianbu.customerservice.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        if(request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(request.address())
                .email(request.email())
                .build();
    }
}
