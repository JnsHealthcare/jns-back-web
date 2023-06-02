package com.jns.backweb.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest
abstract class RestDocsTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static final List<FieldDescriptor> COMMON_RESPONSE_FIELDS = List.of(
            fieldWithPath("code").type(JsonFieldType.STRING).description("응답 커스텀 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
            fieldWithPath("data").description("응답 데이터")
    );

    protected static String makeDataFieldName(String name) {
        return "data." + name;
    }

    protected static Snippet generateApiResponseFields(FieldDescriptor... descriptors) {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.addAll(COMMON_RESPONSE_FIELDS);
        fieldDescriptors.addAll(Arrays.asList(descriptors));
        return responseFields(fieldDescriptors);
    }

    @BeforeEach
    void setUp(WebApplicationContext wc, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wc)
                .apply(documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

}
