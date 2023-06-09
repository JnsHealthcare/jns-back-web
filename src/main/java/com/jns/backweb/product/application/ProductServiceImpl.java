package com.jns.backweb.product.application;

import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductsResponse getProducts() {
        return null;
    }

    @Override
    public ProductDetail getProduct(Long productId) {
        return null;
    }
}
