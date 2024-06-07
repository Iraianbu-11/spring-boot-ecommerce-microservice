package com.iraianbu.customerservice.controller;

import com.iraianbu.customerservice.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer Firstname is required")
        String firstName,
        @NotNull(message = "Customer Lastname is required")
         String lastName,
        @NotNull(message = "Customer Email is required")
        @Email(message = "Invalid Email Address")
        String email,
        Address address
) {
}
