package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.entity.*;

import com.fooddelivery.fooddeliverybackend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Place Order
    public String placeOrder(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found")
                );

        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty"
            );
        }

        // Calculate Total
        double total = 0;

        for (CartItem item : cartItems) {

            total += item.getFoodItem().getPrice()
                    * item.getQuantity();
        }

        // Create Order
        Order order = new Order();

        order.setUser(user);

        order.setTotalAmount(total);

        order.setStatus("PLACED");

        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder =
                orderRepository.save(order);

        // Create Order Items
        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);

            orderItem.setFoodName(
                    item.getFoodItem().getName()
            );

            orderItem.setPrice(
                    item.getFoodItem().getPrice()
            );

            orderItem.setQuantity(
                    item.getQuantity()
            );

            orderItemRepository.save(orderItem);
        }

        // Clear Cart
        cartItemRepository.deleteAll(cartItems);

        return "Order Placed Successfully";
    }

    // Get My Orders
    public List<Order> getMyOrders(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return orderRepository.findByUser(user);
    }
}