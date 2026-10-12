package com.retrohub.repository;

import com.retrohub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tìm danh sách đơn hàng theo ID người dùng
    List<Order> findByUserId(Long userId);

}