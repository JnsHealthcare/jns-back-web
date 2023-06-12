package com.jns.backweb.product.application;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;
import com.jns.backweb.product.application.dto.OptionDetail;
import com.jns.backweb.product.application.dto.OptionInfo;
import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductSimpleInfo;
import com.jns.backweb.product.application.dto.ProductsResponse;
import com.jns.backweb.product.domain.Option;
import com.jns.backweb.product.domain.Product;
import com.jns.backweb.product.repository.OptionRepository;
import com.jns.backweb.product.repository.ProductImageRepository;
import com.jns.backweb.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    private final ProductImageRepository productImageRepository;

    @Override
    public ProductsResponse getProducts() {
        List<Product> products = productRepository.findAllFetchOptions();
        List<ProductSimpleInfo> productSimpleInfos = new ArrayList<>();

        for (Product product : products) {
            List<Long> optionIds = product.getOptions()
                    .stream()
                    .map(Option::getId)
                    .collect(Collectors.toList());
            List<OptionInfo> options = productImageRepository.findMainImagesByOptionIds(optionIds)
                    .stream()
                    .map(productImage -> new OptionInfo(productImage.getOption().getId(), productImage.getUrl()))
                    .collect(Collectors.toList());
            ProductSimpleInfo productSimpleInfo = new ProductSimpleInfo(product.getId(), product.getName(), options);
            productSimpleInfos.add(productSimpleInfo);
        }

        return new ProductsResponse(productSimpleInfos);
    }

    @Override
    public ProductDetail getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new JnsWebApplicationException(ErrorCodeAndMessage.SERVER_ERROR));

        List<Option> options = optionRepository.findAllByProduct(product);

        List<OptionDetail> optionDetails = options.stream().map(OptionDetail::from).collect(Collectors.toList());

        return new ProductDetail(
                product.getId(),
                product.getName(),
                product.getPrice(),
                optionDetails,
                product.getDetailInformationImage());
    }
}
