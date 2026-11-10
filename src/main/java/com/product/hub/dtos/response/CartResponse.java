package com.product.hub.dtos.response;


import java.util.List;
import lombok.Data;

@Data
public class CartResponse {

    private Long cartId;

    private Long userId;

    private List<CartItemResponse> items;

    private Integer totalItems;

    private Double totalAmount;
}