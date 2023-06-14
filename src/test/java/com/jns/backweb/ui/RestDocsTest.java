package com.jns.backweb.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
abstract class RestDocsTest {

    protected MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(new StdDateFormat());

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

    protected static Snippet EMPTY_DATA_RESPONSE_FIELDS = generateApiResponseFields();


}
