package com.jns.backweb.product.application.dto;

import com.jns.backweb.product.domain.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OptionDetail {

    private Long id;
    private String code;
    private String name;
    private List<ImageDto> productImages;


    public static OptionDetail from(Option option) {
        List<ImageDto> imageDtos = option.getImages()
                .stream()
                .map(ImageDto::from)
                .collect(Collectors.toList());
        return new OptionDetail(option.getId(), option.getCode(), option.getName(), imageDtos);
    }
}
