package com.product.hub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.hub.dtos.request.CartRequest;
import com.product.hub.dtos.response.CartItemResponse;
import com.product.hub.dtos.response.CartResponse;
import com.product.hub.entity.Cart;
import com.product.hub.entity.CartItem;
import com.product.hub.entity.Product;
import com.product.hub.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Add product to cart
    @PostMapping("/add/{productId}")
    public ResponseEntity<CartResponse> addToCart(
            @PathVariable Long productId,
            @Valid @RequestBody CartRequest request) {

        Cart cart = cartService.addToCart(
                productId, request.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToResponse(cart));
    }

    // Get logged-in user's cart
    @GetMapping("/my-cart")
    public ResponseEntity<CartResponse> getMyCart() {

        Cart cart = cartService.getMyCart();

        return ResponseEntity.ok(convertToResponse(cart));
    }

    // Update product quantity
    @PutMapping("/update/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable Long productId,
            @Valid @RequestBody CartRequest request) {

        Cart cart = cartService.updateQuantity(
                productId, request.getQuantity());

        return ResponseEntity.ok(convertToResponse(cart));
    }

    // Remove one product
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long productId) {

        cartService.removeFromCart(productId);

        return ResponseEntity.ok("Product removed from cart");
    }

    // Clear entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok("Cart cleared successfully");
    }

    // Convert Cart entity into CartResponse DTO
    private CartResponse convertToResponse(Cart cart) {

        CartResponse response = new CartResponse();

        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUser().getUserId());

        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(this::convertItem)
                .toList();

        response.setItems(items);

        double totalAmount = items.stream()
                .mapToDouble(CartItemResponse::getSubtotal)
                .sum();

        int totalItems = items.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);

        return response;
    }

    // Convert CartItem entity into CartItemResponse DTO
    private CartItemResponse convertItem(CartItem item) {

        Product product = item.getProduct();

        CartItemResponse response = new CartItemResponse();

        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setImage(product.getImage());

        response.setPrice(item.getPriceAtAddition());
        response.setQuantity(item.getQuantity());

        response.setSubtotal(
                item.getPriceAtAddition() * item.getQuantity());

        return response;
    }
}