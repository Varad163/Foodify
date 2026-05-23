package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.OrderDetailsResponse;
import com.fooddelivery.fooddeliverybackend.dto.OrderItemResponse;
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

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    // ==========================
    // PLACE ORDER
    // ==========================
    public String placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"
                        )
                );

        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            return "Cart is empty";
        }

        // Calculate total
        double total = 0;

        for (CartItem item : cartItems) {

            total += item.getFoodItem()
                    .getPrice() * item.getQuantity();
        }

        // Get restaurant
        Restaurant restaurant =
                cartItems.get(0)
                        .getFoodItem()
                        .getRestaurant();

        // Create order
        Order order = Order.builder()
                .user(user)
                .restaurant(restaurant)
                .totalAmount(total)
                .status(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder =
                orderRepository.save(order);

        // Save order items
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem =
                    OrderItem.builder()
                            .order(savedOrder)
                            .foodItem(cartItem.getFoodItem())
                            .quantity(cartItem.getQuantity())
                            .price(
                                    cartItem
                                            .getFoodItem()
                                            .getPrice()
                            )
                            .build();

            orderItemRepository.save(orderItem);
        }

        // Link address
        List<Address> addresses =
                addressRepository.findByUser(user);

        if (!addresses.isEmpty()) {

            Address address =
                    addresses.get(
                            addresses.size() - 1
                    );

            address.setOrder(savedOrder);

            addressRepository.save(address);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "Order placed successfully";
    }

    // ==========================
    // GET MY ORDERS
    // ==========================
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
                .map(order ->
                        OrderResponse.builder()
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
                                .build()
                )
                .collect(Collectors.toList());
    }

    // ==========================
    // UPDATE ORDER STATUS
    // ==========================
    public String updateOrderStatus(
            Long orderId,
            String status
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        OrderStatus newStatus =
                OrderStatus.valueOf(status);

        order.setStatus(newStatus);

        // ==========================
        // ASSIGN DELIVERY PARTNER
        // ==========================
        if (newStatus == OrderStatus.CONFIRMED) {

            DeliveryPartner partner =
                    deliveryPartnerRepository
                            .findFirstByAvailableTrue()
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "No delivery partner available"
                                    )
                            );

            order.setDeliveryPartner(partner);

            // Partner unavailable
            partner.setAvailable(false);

            deliveryPartnerRepository.save(partner);
        }

        // ==========================
        // FREE DELIVERY PARTNER
        // ==========================
        if (newStatus == OrderStatus.DELIVERED) {

            DeliveryPartner partner =
                    order.getDeliveryPartner();

            if (partner != null) {

                partner.setAvailable(true);

                deliveryPartnerRepository.save(partner);
            }
        }

        orderRepository.save(order);

        return "Order updated to " + status;
    }

    // ==========================
    // ORDER DETAILS
    // ==========================
    public OrderDetailsResponse getOrderDetails(
            Long orderId
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        Payment payment =
                paymentRepository.findByOrder(order);

        Address address =
                addressRepository.findByOrder(order);

        List<OrderItem> orderItems =
                orderItemRepository.findByOrder(order);

        List<OrderItemResponse> itemResponses =
                orderItems.stream()
                        .map(item ->
                                OrderItemResponse.builder()
                                        .foodName(
                                                item.getFoodItem()
                                                        .getName()
                                        )
                                        .quantity(
                                                item.getQuantity()
                                        )
                                        .price(
                                                item.getPrice()
                                        )
                                        .build()
                        )
                        .collect(Collectors.toList());

        return OrderDetailsResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .paymentMethod(
                        payment != null
                                ? payment.getPaymentMethod()
                                : "NOT_PAID"
                )
                .paymentStatus(
                        payment != null
                                ? payment.getStatus().name()
                                : "PENDING"
                )
                .city(
                        address != null
                                ? address.getCity()
                                : "N/A"
                )
                .street(
                        address != null
                                ? address.getStreet()
                                : "N/A"
                )
                .items(itemResponses)
                .build();
    }

    // ==========================
    // RESTAURANT OWNER VIEW ORDERS
    // ==========================
    public List<OrderResponse> getOrdersForRestaurant(
            Restaurant restaurant
    ) {

        List<Order> orders =
                orderRepository.findByRestaurant(
                        restaurant
                );

        return orders.stream()
                .map(order ->
                        OrderResponse.builder()
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
                                .build()
                )
                .collect(Collectors.toList());
    }
}