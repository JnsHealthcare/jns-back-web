package com.jns.backweb.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jns.backweb.auth.application.LoginService;
import com.jns.backweb.auth.application.dto.LoginRequest;
import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.model.LoginMember;
import com.jns.backweb.auth.ui.LoginController;
import com.jns.backweb.auth.ui.dto.LoginResponse;
import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LoginControllerTest extends RestDocsTest {

    private static final Snippet SIGNUP_REQUEST_FIELDS = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("회원의 이메일"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("회원의 이름"),
            fieldWithPath("birthDate").type(JsonFieldType.STRING).description("회원의 생년월일"),
            fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("회원의 휴대전화 번호(하이픈(-)없이)"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("회원의 비밀번호")
    );

    private static final Snippet LOCATION_HEADER = responseHeaders(
            headerWithName(HttpHeaders.LOCATION).description("마이페이지 url")
    );

    private static final Snippet NON_DATA_RESPONSE = generateApiResponseFields();


    private static final Snippet LOGIN_REQUEST_FIELDS = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("회원의 이메일"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("회원의 비밀번호")
    );

    private static final Snippet LOGIN_RESPONSE_FIELDS = generateApiResponseFields(
            fieldWithPath(makeDataFieldName("email")).type(JsonFieldType.STRING).description("로그인한 회원의 이메일"),
            fieldWithPath(makeDataFieldName("name")).type(JsonFieldType.STRING).description("로그인한 회원의 이름"),
            fieldWithPath(makeDataFieldName("token")).type(JsonFieldType.STRING).description("엑세스 토큰")
    );

    @Mock
    private LoginService loginService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private LoginController loginController;


    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .apply(documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("자체 회원가입 API에 정상적인 요청을 보내면 201 상태코드와 location을 반환한다.")
    void signup_test_success() throws Exception {
        // given
        String email = "cms05041@gmail.com";
        String name = "최민석";
        LocalDate birthdate = LocalDate.now();
        String phoneNumber = "01012341234";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email, name, birthdate, phoneNumber, password);

        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/auth/signup")
                .content(objectMapper.writeValueAsString(registerRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        Mockito.verify(loginService, times(1)).register(registerRequest);
        resultActions.andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(ApiResponse.success())))
                .andExpect(header().string("LOCATION", "/api/members/me"))
                .andDo(document("auth/signup", SIGNUP_REQUEST_FIELDS,
                        LOCATION_HEADER, NON_DATA_RESPONSE));
    }

    @Test
    @DisplayName("이메일/비밀번호 로그인 API에 정상적인 요청을 보내면 200 상태코드와 회원의 기본정보 및 token을 반환한다.")
    void login_test_success() throws Exception {
        // given
        String email = "cms05041@gmail.com";
        String name = "최민석";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(email, password);

        String accessToken = "accessToken";
        given(authenticationManager.authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(email, password));
        LoginSuccessResult loginSuccessResult = new LoginSuccessResult(email, name, accessToken,"refreshToken", 3000);
        given(loginService.getLoginResult(any())).willReturn(loginSuccessResult);
        LoginResponse loginResponse = LoginResponse.from(loginSuccessResult);



        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/auth/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ApiResponse.success(loginResponse))))
                .andDo(document("auth/login", LOGIN_REQUEST_FIELDS,
                        LOGIN_RESPONSE_FIELDS));
    }

}
