package com.iraianbu.ecommerce.body;

import com.iraianbu.ecommerce.model.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
