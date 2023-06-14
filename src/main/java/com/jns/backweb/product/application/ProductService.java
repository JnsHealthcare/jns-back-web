package com.jns.backweb.product.application;

import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductsResponse;

public interface ProductService {

    ProductsResponse getProducts();

    ProductDetail getProduct(Long productId);
}
