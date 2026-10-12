package com.retrohub.service;

import com.retrohub.entity.*;
import com.retrohub.exception.BadRequestException;
import com.retrohub.exception.ResourceNotFoundException;
import com.retrohub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByUserId(customerId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
    }

    @Transactional
    public Order createOrder(Long userId, Long productId, Integer quantity) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + productId));

        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new ResourceNotFoundException("Không tìm thấy kho cho sản phẩm ID: " + productId);
        }

        if (inventory.getQuantity() < quantity) {
            throw new BadRequestException("Số lượng tồn kho không đủ (Hiện còn: " + inventory.getQuantity() + ")");
        }

        // Trừ tồn kho
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        // Tạo đơn hàng
        Order order = new Order();
        order.setUser(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setPriceAtPurchase(product.getPrice());

        order.getOrderDetails().add(detail);
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return orderRepository.save(order);
    }
}