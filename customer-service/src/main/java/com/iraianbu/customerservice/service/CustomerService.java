package com.iraianbu.customerservice.service;

import com.iraianbu.customerservice.controller.CustomerRequest;
import com.iraianbu.customerservice.model.Customer;
import com.iraianbu.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        mergeCustomer(customer,customerRequest);
       customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.firstName())){
            customer.setFirstName(customerRequest.firstName());
        }
        if(StringUtils.isNotBlank(customerRequest.lastName())){
            customer.setLastName(customerRequest.lastName());
        }
        if(StringUtils.isNotBlank(customerRequest.email())){
            customer.setEmail(customerRequest.email());
        }
        if(customerRequest.address()!=null){
            customer.setAddress(customerRequest.address());
        }
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Boolean getCustomerById(String id) {
        return customerRepository.findById(id).isPresent();
    }

    public void deleteCustomer(String id){
         customerRepository.deleteById(id);
    }
}
