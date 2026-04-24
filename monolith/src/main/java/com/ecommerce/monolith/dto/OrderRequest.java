package com.ecommerce.monolith.dto;

public class OrderRequest {
    private Long customerId;
    private Long productId;
    private int quantity;

    public Long getCustomerId() { return customerId; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}
