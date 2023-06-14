package com.jns.backweb.product.repository;

import com.jns.backweb.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select distinct p from Product p join fetch p.options")
    List<Product> findAllFetchOptions();
}
