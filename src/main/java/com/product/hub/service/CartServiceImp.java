package com.product.hub.service;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.hub.entity.Cart;
import com.product.hub.entity.CartItem;
import com.product.hub.entity.Product;
import com.product.hub.entity.User;
import com.product.hub.repository.CartItemRepository;
import com.product.hub.repository.CartRepository;
import com.product.hub.repository.ProductRepository;
import com.product.hub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Get currently logged-in user
    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User is not authenticated");
        }

        String username = authentication.getName();

        return userRepository.findByUserName(username);
               
    }

    // Find existing cart or create a new one
    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUser_UserId(user.getUserId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItems(new ArrayList<>());
                    return cartRepository.save(cart);
                });
    }

    // Add product to cart
    @Override
    public Cart addToCart(Long productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        User user = getLoggedInUser();

        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() == null ||
                product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient product stock");
        }

        CartItem item = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(
                        cart.getCartId(), productId)
                .orElse(null);

        if (item != null) {

            int updatedQuantity = item.getQuantity() + quantity;

            if (updatedQuantity > product.getQuantity()) {
                throw new RuntimeException("Insufficient product stock");
            }

            item.setQuantity(updatedQuantity);

        } else {

            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPriceAtAddition(product.getNetPrice());

            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return cart;
    }

    // Get logged-in user's cart
    @Override
    public Cart getMyCart() {

        User user = getLoggedInUser();

        return cartRepository.findByUser_UserId(user.getUserId())
                .orElseGet(() -> getOrCreateCart(user));
    }

    // Update product quantity
    @Override
    public Cart updateQuantity(Long productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(
                        cart.getCartId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        Product product = item.getProduct();

        if (product.getQuantity() == null ||
                quantity > product.getQuantity()) {
            throw new RuntimeException("Insufficient product stock");
        }

        item.setQuantity(quantity);

        return cart;
    }

    // Remove one product from cart
    @Override
    public void removeFromCart(Long productId) {

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(
                        cart.getCartId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
    }

    // Remove all products from cart
    @Override
    public void clearCart() {

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
    }
}