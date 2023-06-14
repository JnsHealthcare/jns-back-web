package com.jns.backweb.product.repository;

import com.jns.backweb.product.domain.Option;
import com.jns.backweb.product.domain.Product;
import com.jns.backweb.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {


    @Query("select pi from ProductImage pi where pi.option.id in :optionIds and pi.order = 1")
    List<ProductImage> findMainImagesByOptionIds(List<Long> optionIds);
}
