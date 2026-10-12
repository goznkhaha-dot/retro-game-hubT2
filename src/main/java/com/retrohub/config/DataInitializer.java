package com.retrohub.config;

import com.retrohub.entity.Product;
import com.retrohub.entity.Role;
import com.retrohub.entity.User;
import com.retrohub.repository.ProductRepository;
import com.retrohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Tạo tài khoản mặc định
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setFullName("System Administrator");
            admin.setEmail("admin@retrohub.com");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user123");
            user.setFullName("Default User");
            user.setEmail("user@retrohub.com");
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        if (userRepository.findByUsername("customer").isEmpty()) {
            User customer = new User();
            customer.setUsername("customer");
            customer.setPassword("customer123");
            customer.setFullName("Khách hàng mẫu");
            customer.setEmail("customer@retrohub.com");
            customer.setRole(Role.CUSTOMER);
            userRepository.save(customer);
        }

        // 2. Tạo danh sách sản phẩm Retro
        if (productRepository.count() == 0) {

            Product p1 = new Product();
            p1.setName("Game Boy Color - Yellow Edition");
            p1.setPrice(BigDecimal.valueOf(1850000));
            p1.setDescription("Máy chơi game cầm tay huyền thoại Game Boy Color màu vàng chanh, màn hình sắc nét, phím bấm nảy.");
            p1.setImageUrl("https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=600&auto=format&fit=crop");

            Product p2 = new Product();
            p2.setName("NES Classic Mini Edition");
            p2.setPrice(BigDecimal.valueOf(2200000));
            p2.setDescription("Tích hợp sẵn 30 tựa game 8-bit kinh điển như Super Mario Bros, Contra, Legend of Zelda.");
            p2.setImageUrl("https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=600&auto=format&fit=crop");

            Product p3 = new Product();
            p3.setName("PlayStation 1 Classic Fullbox");
            p3.setPrice(BigDecimal.valueOf(2500000));
            p3.setDescription("Hệ máy Sony PS1 huyền thoại dành cho dân sưu tầm. Đi kèm 2 tay cầm zin và đầy đủ dây cáp.");
            p3.setImageUrl("https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=600&auto=format&fit=crop");

            Product p4 = new Product();
            p4.setName("Game Boy Advance SP Cobalt Blue");
            p4.setPrice(BigDecimal.valueOf(2100000));
            p4.setDescription("Mẫu GBA SP vỏ gập màu xanh khoáng, màn hình đèn nền sáng rõ, pin sạc liên tục 8 tiếng.");
            p4.setImageUrl("https://images.unsplash.com/photo-1531525645387-7f14be1bbea7?w=600&auto=format&fit=crop");

            productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
            System.out.println(">>> Đã khởi tạo thành công 4 sản phẩm mẫu Retro Game!");
        }

        System.out.println(">>> Dữ liệu Retro Game Hub đã sẵn sàng!");
    }
}