package com.retrohub.controller;

import com.retrohub.entity.User;
import com.retrohub.service.OrderService;
import com.retrohub.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam Long productId,
                              @RequestParam Integer quantity,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        orderService.createOrder(user.getId(), productId, quantity);
        return "redirect:/orders/my-orders";
    }

    @GetMapping("/my-orders")
    public String myOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("orders", orderService.getOrdersByCustomer(user.getId()));
        return "my-orders";
    }
}