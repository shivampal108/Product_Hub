package com.product.hub.service;


//import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;

import com.product.hub.dtos.request.ProductRequest;
import com.product.hub.dtos.response.ProductRespoonse;
import com.product.hub.entity.Product;

public interface ProductService {
	
	
	public ProductRespoonse addProduct(ProductRequest request);
	
	public ProductRespoonse updateProduct(ProductRequest request,Long id);
//	@Cacheable(value = "products", key = "#id")
	
	public ProductRespoonse viewProduct(Long id);
	

	
	public String deleteProduct(Long id);
	
	public Page<Product> pageWise(int pageSize, int pageNo);



	

}
