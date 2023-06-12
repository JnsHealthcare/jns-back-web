package com.jns.backweb.product.application.dto;

import com.jns.backweb.product.domain.ProductImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ImageDto {

    private Integer order;
    private String imageUrl;

    public static ImageDto from(ProductImage productImage) {
        return new ImageDto(productImage.getOrder(), productImage.getUrl());
    }


}
