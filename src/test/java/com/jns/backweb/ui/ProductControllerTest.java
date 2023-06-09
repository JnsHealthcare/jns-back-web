package com.jns.backweb.ui;

import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.product.application.ProductService;
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

import static org.mockito.BDDMockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends RestDocsTest {

    private static final Snippet GET_PRODUCTS_RESPONSE_FIELDS = generateApiResponseFields(
            fieldWithPath(makeDataFieldName("products[].id")).type(JsonFieldType.NUMBER).description("제품 아이디"),
            fieldWithPath(makeDataFieldName("products[].code")).type(JsonFieldType.STRING).description("제품 코드"),
            fieldWithPath(makeDataFieldName("products[].name")).type(JsonFieldType.STRING).description("제품 이름"),
            fieldWithPath(makeDataFieldName("products[].mainImageUrl")).type(JsonFieldType.STRING).description("제품 메인 이미지"),
            fieldWithPath(makeDataFieldName("products[].images")).type(JsonFieldType.ARRAY).description("제품 이미지들")
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
        List<String> images = List.of(mainImageUrl, mainImageUrl, mainImageUrl);
        ProductSimpleInfo limeSquare = new ProductSimpleInfo(1L, "lime1-grey", "lime square ", mainImageUrl, images);
        ProductSimpleInfo limeTrapezoid = new ProductSimpleInfo(2L, "lime2-white", "lime trapezoid", mainImageUrl, images);

        ProductsResponse productsResponse = new ProductsResponse(List.of(limeSquare, limeTrapezoid));
        when(productService.getProducts()).thenReturn(productsResponse);

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
    @DisplayName("제품 목록 API에 정상적인 요청을 보내면, 제품목록에 대한 데이터와 200 OK를 응답한다.")
    void getProduct_test_success() throws Exception {
        String mainImageUrl = "https://avatars.githubusercontent.com/u/134758318?s=200&v=4";
        List<String> images = List.of(mainImageUrl, mainImageUrl, mainImageUrl);

        ProductsResponse productsResponse = new ProductsResponse(List.of(limeSquare, limeTrapezoid));
        when(productService.getProducts()).thenReturn(productsResponse);

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




}
