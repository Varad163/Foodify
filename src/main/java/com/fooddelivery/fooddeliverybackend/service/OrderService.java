package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.OrderResponse;

import com.fooddelivery.fooddeliverybackend.entity.*;

import com.fooddelivery.fooddeliverybackend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // PLACE ORDER
    public String placeOrder(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"
                        )
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

        order.setStatus(OrderStatus.PLACED);

        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder =
                orderRepository.save(order);

        // Create Order Items
        for (CartItem item : cartItems) {

            OrderItem orderItem =
                    new OrderItem();

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

    // GET MY ORDERS
    public List<OrderResponse> getMyOrders(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        List<Order> orders =
                orderRepository.findByUser(user);

        return orders.stream()

                .map(order -> OrderResponse.builder()

                        .orderId(order.getId())

                        .totalAmount(
                                order.getTotalAmount()
                        )

                        .status(
                                order.getStatus().name()
                        )

                        .createdAt(
                                order.getCreatedAt()
                        )

                        .build())

                .collect(Collectors.toList());
    }

    // UPDATE ORDER STATUS
    public String updateOrderStatus(

            Long orderId,

            OrderStatus status
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(status);

        orderRepository.save(order);

        return "Order status updated successfully";
    }
}