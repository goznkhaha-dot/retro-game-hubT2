package com.retrohub.repository;

import com.retrohub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Tìm người dùng theo tên đăng nhập
    Optional<User> findByUsername(String username);

    // Kiểm tra xem tên đăng nhập đã tồn tại chưa
    boolean existsByUsername(String username);
}