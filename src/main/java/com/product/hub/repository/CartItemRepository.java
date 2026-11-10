package com.product.hub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.product.hub.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(
            Long cartId, Long productId);

    List<CartItem> findByCart_CartId(Long cartId);

}