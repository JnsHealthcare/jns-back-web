package com.jns.backweb.auth.ui;

import com.jns.backweb.auth.application.LoginService;
import com.jns.backweb.auth.application.dto.LoginRequest;
import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import com.jns.backweb.auth.application.dto.OauthRequest;
import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.ui.dto.EmailCheckRequest;
import com.jns.backweb.auth.ui.dto.LoginResponse;
import com.jns.backweb.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {



    private final LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {

        LoginSuccessResult loginResult = loginService.passwordLogin(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(LoginResponse.from(loginResult)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> registerMember(@Valid @RequestBody RegisterRequest registerRequest) {

        loginService.register(registerRequest);
        URI location = UriComponentsBuilder.fromPath("/api/members/me").build().toUri();

        return ResponseEntity.created(location).body(ApiResponse.success());
    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<Void>> checkAvailableEmail(@Valid @RequestBody EmailCheckRequest emailCheckRequest) {
        String email = emailCheckRequest.getEmail();
        loginService.checkAvailableEmail(email);

        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/oauth/{provider}")
    public ResponseEntity<ApiResponse<LoginResponse>> oauthLogin(@PathVariable String provider, @RequestBody OauthRequest oauthRequest) {

        LoginSuccessResult loginSuccessResult = loginService.oauthLogin(provider, oauthRequest.getAuthorizationCode());
        LoginResponse loginResponse = LoginResponse.from(loginSuccessResult);

        // set cookie

        return ResponseEntity.ok(ApiResponse.success(loginResponse));
    }




}
