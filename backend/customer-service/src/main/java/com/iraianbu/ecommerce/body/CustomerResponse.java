package com.iraianbu.ecommerce.body;

import com.iraianbu.ecommerce.model.Address;

import java.io.Serializable;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) implements Serializable {
}
