package com.product.hub.service;

import java.util.List;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.product.hub.dtos.request.ProductRequest;
import com.product.hub.dtos.response.ProductRespoonse;
import com.product.hub.entity.Product;
import com.product.hub.exception.ProductNotFoundException;
import com.product.hub.repository.ProductRepository;


@Service

public class ProductServiceImp implements ProductService {
	
	@Autowired
	private ProductRepository pRepo;

	

	@Override
	public ProductRespoonse addProduct(ProductRequest request) {
		
		Product p=new Product();
		
		BeanUtils.copyProperties(request,p);
		
	double netPrice= p.getPrice()-(p.getPrice()*p.getDiscount()/100);
		
	p.setNetPrice(netPrice);
		
		
		
//		System.out.println(request.getPrice());
//		System.out.println(p.getPrice());

		
		pRepo.save(p);
		
		
		ProductRespoonse response= ProductRespoonse.builder().build();
		
		BeanUtils.copyProperties(p, response);
		
		
		return response;
	}

	@Override
	public ProductRespoonse updateProduct(ProductRequest request,Long id) {
		
		Product p=pRepo.findById(id).orElseThrow(()-> new ProductNotFoundException("product not there!"));
		
		
		BeanUtils.copyProperties(request, p);
		double netPrice= p.getPrice()-(p.getPrice()*p.getDiscount()/100);
		
		p.setNetPrice(netPrice);
		
		pRepo.save(p);
		
	ProductRespoonse response= ProductRespoonse.builder().build();
		
		BeanUtils.copyProperties(p, response);
		
		
		return response ;
	}

	@Override
	public ProductRespoonse viewProduct(Long id) {
		
		Product p=pRepo.findById(id).orElseThrow(()-> new ProductNotFoundException("product not there!"));
		
		
ProductRespoonse response= ProductRespoonse.builder().build();
		
		BeanUtils.copyProperties(p, response);
		
		
		
		
		
		return response ;
	}



	  // Helper: create a sample entity
    private Product createProduct() {
        Product p = new Product();
        p.setProductId(1L);
        p.setProductName("Laptop");
        p.setProductDesc("Gaming laptop");
        p.setImage("laptop.png");
        p.setPrice(1000.0);
        p.setDiscount(10.0);
        p.setNetPrice(900.0);
        p.setQuantity(5);
        return p;
    }
	@Override
	public Page<Product> pageWise(int pageSize, int pageNo) {
		
		Pageable pages= PageRequest.of(pageNo, pageSize);
		
		Page<Product> products =pRepo.findAll(pages);
		
		return products;
	}

	@Override
	public String deleteProduct(Long id) {
		
		Product p=pRepo.findById(id).orElseThrow(()-> new ProductNotFoundException("product not there!"));

		pRepo.deleteById(id);
		
		return "product deleted id : "+id;
	}

}
