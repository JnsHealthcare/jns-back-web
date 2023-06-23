package com.jns.backweb.product.application;

import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductsResponse;
import com.jns.backweb.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @Test
    void getProducts() {
        ProductsResponse products = productService.getProducts();
        assertThat(products.getProducts().size()).isEqualTo(2);
    }

    @Test
    void getProduct() {

        ProductDetail product = productService.getProduct(1L);
        assertThat(product.getProductId()).isEqualTo(1L);
        assertThat(product.getOptions().size()).isEqualTo(3);
    }
}
