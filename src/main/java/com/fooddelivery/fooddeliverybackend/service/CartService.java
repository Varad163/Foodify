package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.CartRequest;
import com.fooddelivery.fooddeliverybackend.dto.CartResponse;

import com.fooddelivery.fooddeliverybackend.entity.*;

import com.fooddelivery.fooddeliverybackend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Add To Cart
    public String addToCart(
            CartRequest request,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });

        FoodItem foodItem = foodRepository
                .findById(request.getFoodId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Food item not found"
                        )
                );

        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);

        cartItem.setFoodItem(foodItem);

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return "Item Added To Cart";
    }

    // Get My Cart
    public List<CartResponse> getMyCart(
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

        return cartItems.stream()

                .map(item -> CartResponse.builder()

                        .foodId(
                                item.getFoodItem().getId()
                        )

                        .foodName(
                                item.getFoodItem().getName()
                        )

                        .price(
                                item.getFoodItem().getPrice()
                        )

                        .quantity(
                                item.getQuantity()
                        )

                        .restaurantName(
                                item.getFoodItem()
                                        .getRestaurant()
                                        .getName()
                        )

                        .build())

                .collect(Collectors.toList());
    }
}