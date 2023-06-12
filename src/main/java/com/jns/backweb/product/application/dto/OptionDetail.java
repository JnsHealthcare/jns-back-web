package com.jns.backweb.product.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OptionDetail {

    private Long id;
    private String code;
    private String name;
    private List<ImageDto> productImages;
}
