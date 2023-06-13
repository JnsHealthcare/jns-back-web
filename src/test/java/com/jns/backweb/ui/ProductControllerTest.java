package com.jns.backweb.ui;

import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.product.application.ProductService;
import com.jns.backweb.product.application.dto.ImageDto;
import com.jns.backweb.product.application.dto.OptionDetail;
import com.jns.backweb.product.application.dto.OptionInfo;
import com.jns.backweb.product.application.dto.ProductDetail;
import com.jns.backweb.product.application.dto.ProductSimpleInfo;
import com.jns.backweb.product.application.dto.ProductsResponse;
import com.jns.backweb.product.ui.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends RestDocsTest {

    private static final Snippet GET_PRODUCTS_RESPONSE_FIELDS = generateApiResponseFields(
            fieldWithPath(makeDataFieldName("products[].id")).type(JsonFieldType.NUMBER).description("제품 아이디"),
            fieldWithPath(makeDataFieldName("products[].name")).type(JsonFieldType.STRING).description("제품 이름"),
            fieldWithPath(makeDataFieldName("products[].options[].id")).type(JsonFieldType.NUMBER).description("옵션의 아이디"),
            fieldWithPath(makeDataFieldName("products[].options[].image")).type(JsonFieldType.STRING).description("옵션 이미지")
    );

    private static final Snippet PRODUCT_PATH_PARAMETER = pathParameters(parameterWithName("productId").description("제품의 아이디"));

    private static final Snippet GET_PRODUCT_ONE_RESPONSE_FIELDS = generateApiResponseFields(
            fieldWithPath(makeDataFieldName("productId")).type(JsonFieldType.NUMBER).description("제품 아이디"),
            fieldWithPath(makeDataFieldName("name")).type(JsonFieldType.STRING).description("제품 이름"),
            fieldWithPath(makeDataFieldName("price")).type(JsonFieldType.NUMBER).description("제품의 가격"),
            fieldWithPath(makeDataFieldName("options[].id")).type(JsonFieldType.NUMBER).description("옵션의 아이디"),
            fieldWithPath(makeDataFieldName("options[].code")).type(JsonFieldType.STRING).description("옵션의 제품 코드"),
            fieldWithPath(makeDataFieldName("options[].name")).type(JsonFieldType.STRING).description("옵션 정보를 포함한 제품 이름"),
            fieldWithPath(makeDataFieldName("options[].productImages[].order")).type(JsonFieldType.NUMBER).description("상세 사진 순서"),
            fieldWithPath(makeDataFieldName("options[].productImages[].imageUrl")).type(JsonFieldType.STRING).description("상세 사진 url"),
            fieldWithPath(makeDataFieldName("detailInformation")).type(JsonFieldType.STRING).description("상세 제품 소개 사진 url")
    );

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .apply(documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


    @Test
    @DisplayName("제품 목록 API에 정상적인 요청을 보내면, 제품목록에 대한 데이터와 200 OK를 응답한다.")
    void getProducts_test_success() throws Exception {
        String mainImageUrl = "https://avatars.githubusercontent.com/u/134758318?s=200&v=4";
        OptionInfo optionInfo1 = new OptionInfo(1L, mainImageUrl);
        OptionInfo optionInfo2 = new OptionInfo(2L, mainImageUrl);

        ProductSimpleInfo limeSquare = new ProductSimpleInfo(1L, "lime1 square", List.of(optionInfo1, optionInfo2));
        ProductSimpleInfo limeTrapezoid = new ProductSimpleInfo(2L, "lime1 trapezoid", List.of(optionInfo1, optionInfo2));

        ProductsResponse productsResponse = new ProductsResponse(List.of(limeSquare, limeTrapezoid));
        given(productService.getProducts()).willReturn(productsResponse);

        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/products")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ApiResponse.success(productsResponse))))
                .andDo(document("product/all", GET_PRODUCTS_RESPONSE_FIELDS));
    }

    @Test
    @DisplayName("제품 상세 API에 정상적인 요청을 보내면, 제품에 대한 데이터와 200 OK를 응답한다.")
    void getProduct_test_success() throws Exception {
        //given
        String mainImageUrl = "https://avatars.githubusercontent.com/u/134758318?s=200&v=4";
        OptionDetail whiteOption = new OptionDetail(1L, "lime1-sq-wh", "lime1 square white", List.of(new ImageDto(1, mainImageUrl)));
        OptionDetail blackOption = new OptionDetail(2L, "lime1-sq-bk", "lime1 square black", List.of(new ImageDto(1, mainImageUrl)));

        Long productId = 1L;
        String name = "Lime1 Square";
        Double price = 1_000_000D;
        List<OptionDetail> options = List.of(whiteOption, blackOption);
        String detailInformation = mainImageUrl;

        ProductDetail productDetail = new ProductDetail(productId, name, price, options, detailInformation);
        given(productService.getProduct(productId)).willReturn(productDetail);
        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/products/{productId}", productId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ApiResponse.success(productDetail))))
                .andDo(document("product/one", PRODUCT_PATH_PARAMETER,
                        GET_PRODUCT_ONE_RESPONSE_FIELDS));

    }




}
