package com.jns.backweb.product.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class ProductDetail {

    private Long productId;
    private String name;
    private Double price;
    private List<OptionDetail> options;
    private String detailInformation;


}
