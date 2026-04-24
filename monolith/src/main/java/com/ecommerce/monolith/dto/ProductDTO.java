package com.ecommerce.monolith.dto;

public class ProductDTO {
    public final Long id;
    public final String name;
    public final double price;

    public ProductDTO(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
