package com.product.hub.service;

import com.product.hub.entity.Cart;

public interface CartService {

    Cart addToCart(Long productId, Integer quantity);

    Cart getMyCart();

    Cart updateQuantity(Long productId, Integer quantity);

    void removeFromCart(Long productId);

    void clearCart();
}