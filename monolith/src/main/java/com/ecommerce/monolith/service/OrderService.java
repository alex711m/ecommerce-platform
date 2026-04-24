package com.ecommerce.monolith.service;

import com.ecommerce.monolith.dto.OrderDetails;
import com.ecommerce.monolith.dto.OrderRequest;
import com.ecommerce.monolith.dto.ProductDTO;
import com.ecommerce.monolith.model.Customer;
import com.ecommerce.monolith.model.Order;
import com.ecommerce.monolith.model.Product;
import com.ecommerce.monolith.repository.CustomerRepository;
import com.ecommerce.monolith.repository.OrderRepository;
import com.ecommerce.monolith.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<OrderDetails> findAllFull() {
        return orderRepository.findAll().stream()
                .map(this::toOrderDetails)
                .toList();
    }

    @Transactional
    public void placeOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("Stock insuffisant");
        }

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        orderRepository.save(new Order(customer.getId(), product.getId(), request.getQuantity()));
    }

    private OrderDetails toOrderDetails(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new IllegalStateException("Client introuvable pour la commande " + order.getId()));

        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(() -> new IllegalStateException("Produit introuvable pour la commande " + order.getId()));

        double discount = "ETUDIANT".equals(customer.getStatus()) ? 0.80 : 1.0;
        double finalPrice = product.getPrice() * order.getQuantity() * discount;

        return new OrderDetails(
                order.getId(),
                order.getQuantity(),
                customer,
                new ProductDTO(product.getId(), product.getName(), product.getPrice()),
                finalPrice
        );
    }
}
