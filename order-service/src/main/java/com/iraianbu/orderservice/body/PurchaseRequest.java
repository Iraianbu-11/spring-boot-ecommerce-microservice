package com.iraianbu.orderservice.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
    @NotNull(message = "Product is Mandatory")
    Integer productId,
    @Positive(message = "Quantity is mandatory")
    double quantity
) {
}
