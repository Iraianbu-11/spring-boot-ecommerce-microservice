package com.iraianbu.ecommerce.controller;

import com.iraianbu.ecommerce.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record CustomerRequest(String id,
                              @NotNull(message = "Customer Firstname is required")
                              String firstName,
                              @NotNull(message = "Customer Lastname is required")
                              String lastName,
                              @NotNull(message = "Customer Email is required")
                              @Email(message = "Invalid Email Address")
                              String email,
                              Address address) implements Serializable {
}
