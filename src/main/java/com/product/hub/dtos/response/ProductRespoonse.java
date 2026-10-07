package com.product.hub.dtos.response;

import java.io.Serializable;


import com.product.hub.enums.Category;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRespoonse implements Serializable {
	
	private Long productId;
	
	private String productName;
	
	private String productDesc;
	
	private String image;
	
	private Double price;
	
	private Double discount;
	
	private Integer quantity;
	
	private Double netPrice;
	
	@Enumerated(EnumType.STRING)

	private Category category;


}
