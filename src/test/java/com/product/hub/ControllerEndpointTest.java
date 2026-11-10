package com.product.hub;





import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.product.hub.controller.ProductController;
import com.product.hub.dtos.request.ProductRequest;
import com.product.hub.dtos.response.ProductRespoonse;
import com.product.hub.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import com.product.hub.dtos.request.ProductRequest;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestCacheConfig.class)
public class ControllerEndpointTest {

	    @Autowired
	    private MockMvc mockMvc;

	    @MockitoBean
	    private ProductService productService;

	    @Test
	    void getProductTest() throws Exception {

	        ProductRespoonse product = new ProductRespoonse();
	        product.setProductId(1L);
	        product.setProductName("Laptop");

	        when(productService.viewProduct(1L))
	                .thenReturn(product);

	        mockMvc.perform(get("/api-product/view/1"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.data.productId").value(1))
	                .andExpect(jsonPath("$.data.productName").value("Laptop"));
	        
	    }
	    
	    

@Test
void addProductTest() throws Exception {

    ProductRequest req = new ProductRequest();
    req.setProductName("Laptop");
    req.setProductDesc("Gaming laptop");
    req.setPrice(50000.0);
    req.setDiscount(10.0);
    req.setQuantity(5);

    ProductRespoonse response = new ProductRespoonse();
    response.setProductId(1L);
    response.setProductName("Laptop");

    when(productService.addProduct(any(ProductRequest.class)))
            .thenReturn(response);

    mockMvc.perform(post("/api-product/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "productName": "Laptop",
                    "productDesc": "Gaming laptop",
                    "price": 50000.0,
                    "discount": 10.0,
                    "quantity": 5
                }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.productId").value(1))
            .andExpect(jsonPath("$.data.productName").value("Laptop"));

    verify(productService).addProduct(any(ProductRequest.class));
}


	}