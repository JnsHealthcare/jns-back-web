package com.jns.backweb.product.repository;

import com.jns.backweb.product.domain.Option;
import com.jns.backweb.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select distinct o from Option o join fetch o.images where o.product = :product")
    List<Option> findAllByProduct(Product product);

}
