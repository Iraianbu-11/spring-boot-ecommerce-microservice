package com.iraianbu.orderservice.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
