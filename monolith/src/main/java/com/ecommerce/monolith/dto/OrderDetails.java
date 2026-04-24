package com.ecommerce.monolith.dto;

import com.ecommerce.monolith.model.Customer;

public class OrderDetails {
    public final Long orderId;
    public final int quantity;
    public final Customer customer;
    public final ProductDTO product;
    public final double finalPrice;

    public OrderDetails(Long orderId, int quantity, Customer customer, ProductDTO product, double finalPrice) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
        this.finalPrice = finalPrice;
    }
}
