package com.jns.backweb.product.ui;

import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.product.application.ProductService;
import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProductsResponse>> readAllProducts() {

        ProductsResponse products = productService.getProducts();

        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDetail>> getProduct(@PathVariable Long productId) {

        ProductDetail product = productService.getProduct(productId);

        return ResponseEntity.ok(ApiResponse.success(product));
    }
}
