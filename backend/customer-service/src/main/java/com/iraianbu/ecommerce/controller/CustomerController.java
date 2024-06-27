package com.iraianbu.ecommerce.controller;

import com.iraianbu.ecommerce.body.CustomerResponse;
import com.iraianbu.ecommerce.model.Customer;
import com.iraianbu.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping("/dev")
    public String dev(){
        return "Success";
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> getCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @DeleteMapping("/{customer-id} ")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") String customerId) {
         customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }

}
