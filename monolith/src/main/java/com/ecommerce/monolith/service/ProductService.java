package com.ecommerce.monolith.service;

import com.ecommerce.monolith.model.Product;
import com.ecommerce.monolith.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public boolean decreaseStock(Long id, int quantity) {
        return productRepository.findById(id).map(product -> {
            if (product.getStock() < quantity) return false;
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }
}
