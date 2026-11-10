
package com.product.hub;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.product.hub.dtos.request.ProductRequest;
import com.product.hub.dtos.response.ProductRespoonse;
import com.product.hub.entity.Product;
import com.product.hub.enums.Category;
import com.product.hub.exception.ProductNotFoundException;
import com.product.hub.repository.ProductRepository;
import com.product.hub.service.ProductServiceImp;

@ExtendWith(MockitoExtension.class)
class ProductHubApplicationTests {

    @Mock
    ProductRepository repo;

    @InjectMocks
    ProductServiceImp service;
    
    // Helper: create a sample entity
//    private Product createProduct() {
//        Product p = new Product();
//        p.setProductId(1L);
//        p.setProductName("Laptop");
//        p.setProductDesc("Gaming laptop");
//        p.setImage("laptop.png");
//        p.setPrice(1000.0);
//        p.setDiscount(10.0);
//        p.setNetPrice(900.0);
//        p.setQuantity(5);
//        return p;
//    }
    
    // Helper: create a request DTO
    private ProductRequest createRequest() {
        ProductRequest request = new ProductRequest();
        request.setProductName("Laptop");
        request.setProductDesc("Gaming laptop");
        request.setImage("laptop.png");
        request.setPrice(1000.0);
        request.setDiscount(10.0);
        request.setQuantity(5);
        return request;
    }

    
    
    
    
    
    // 1. ADD PRODUCT
    @Test
    void testAddProduct() {
        // Arrange
        ProductRequest request = createRequest();

        when(repo.save(any(Product.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        ProductRespoonse response = service.addProduct(request);

        ArgumentCaptor<Product> captor =
                ArgumentCaptor.forClass(Product.class);
        verify(repo).save(captor.capture());

        
        // Assert
        assertEquals("Laptop", response.getProductName());
        assertEquals(900.0, response.getNetPrice(), 0.001);

        verify(repo).save(any(Product.class));
    }
    
    
    
    @Test
    void deleteProduct() {
    	
    	 Product product = new Product();
         product.setProductId(1L);
         product.setProductName("deepak");
         product.setProductDesc("lala");
         product.setImage("32bhe");
         product.setPrice(45.0);
         product.setDiscount(34.0);
         product.setNetPrice(29.7);
         product.setQuantity(34);
         product.setCategory(Category.BOOKS);
    	
         when(repo.findById(1L))
         .thenReturn(Optional.of(product));   
         
         String resp = service.deleteProduct(1l);

         assertEquals("product deleted id : 1", resp);
    	
    }
    
    

    @Test
    void getproduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("deepak");
        product.setProductDesc("lala");
        product.setImage("32bhe");
        product.setPrice(45.0);
        product.setDiscount(34.0);
        product.setNetPrice(29.7);
        product.setQuantity(34);
        product.setCategory(Category.BOOKS);

        when(repo.findById(1L))
                .thenReturn(Optional.of(product));

        // Act
        ProductRespoonse resp = service.viewProduct(1L);
        // Assert
        assertEquals("deepak", resp.getProductName());
        assertEquals(29.7, resp.getNetPrice(), 0.001);
    }
    
    
    @Test
    void testProductFailure() {
    	
    	
    	when(repo.findById(99l)).thenReturn(Optional.empty());
    	
        assertThrows(ProductNotFoundException.class, () -> service.viewProduct(99L));
    	
        verify(repo).findById(99L);

    }
    
    
    
    
}
