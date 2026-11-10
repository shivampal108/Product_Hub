package com.product.hub.dtos.response;

import lombok.Data;

@Data
public class CartItemResponse {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private String image;

    private Double price;

    private Integer quantity;

    private Double subtotal;
}