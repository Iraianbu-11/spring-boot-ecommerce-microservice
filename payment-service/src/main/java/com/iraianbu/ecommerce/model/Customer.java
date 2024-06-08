package com.iraianbu.ecommerce.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "Firstname is Required")
        String firstName,
        @NotNull(message = "Lastname is Required")
        String lastName,
        @NotNull(message = "Email is Required")
        @Email(message = "Invalid Mail")
        String email
) {
}
