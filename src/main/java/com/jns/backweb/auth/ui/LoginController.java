package com.jns.backweb.auth.ui;

import com.jns.backweb.auth.application.LoginService;
import com.jns.backweb.auth.application.dto.LoginRequest;
import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.ui.dto.LoginResponse;
import com.jns.backweb.auth.util.CookieUtil;
import com.jns.backweb.common.dto.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private static final String REFRESH_TOKEN_KEY = "rtk";

    private final LoginService loginService;
    //private final AuthenticationManager authenticationManager;



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        //Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        LoginSuccessResult loginResult = loginService.getLoginResult(authentication);
//        CookieUtil.addCookie(response, REFRESH_TOKEN_KEY, loginResult.getRefreshToken(), loginResult.getRefreshTokenDuration());

//        return ResponseEntity.ok(ApiResponse.success(LoginResponse.from(loginResult)));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("hello jns");
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> registerMember(@Valid @RequestBody RegisterRequest registerRequest) {

        loginService.register(registerRequest);

        URI location = UriComponentsBuilder.fromPath("/api/members/me").build().toUri();

        return ResponseEntity.created(location).build();
    }


}
