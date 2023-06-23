package com.jns.backweb.auth.application;

import com.jns.backweb.auth.application.dto.LoginRequest;
import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.exception.DuplicatedEmailException;
import com.jns.backweb.auth.exception.MemberNotFoundException;
import com.jns.backweb.auth.model.MemberInfo;
import com.jns.backweb.auth.model.OAuthAttribute;
import com.jns.backweb.auth.ui.dto.LoginResponse;
import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final OauthClientRepository oauthClientRepository;


    public LoginSuccessResult passwordLogin(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
        member.validatePassword(encodedPassword);

        return generateLoginResult(member);
    }

    @Transactional
    public void register(RegisterRequest registerRequest) {

        if(memberRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicatedEmailException();
        };
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Member member = Member.of(registerRequest.getEmail(), registerRequest.getName(),
                registerRequest.getBirthDate(), registerRequest.getPhoneNumber(), encodedPassword);

        memberRepository.save(member);
        log.debug("jns-signup [id={}, email={}]", member.getId(), member.getEmail());
    }

    public void checkAvailableEmail(String email) {

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    public LoginSuccessResult oauthLogin(String providerName, String code) {
        OauthClient oauthClient = oauthClientRepository.findOauthClient(providerName);
        Map<String, Object> oauthMemberInfo = oauthClient.getOauthMemberInfo(code);

        MemberInfo memberInfo = OAuthAttribute.toMemberInfo(providerName, oauthMemberInfo);
        Member member = memberRepository.findByEmail(memberInfo.getEmail())
                .orElseGet(() -> memberRepository.save(Member.ofSimpleMember(memberInfo.getEmail(), memberInfo.getName())));
        return generateLoginResult(member);
    }

    private LoginSuccessResult generateLoginResult(Member member) {
        String accessToken = jwtProvider.generateAccessToken(member.getId());
        String refreshToken = jwtProvider.generateRefreshToken(member.getId());

        return new LoginSuccessResult(
                member.getEmail(), member.getName(),
                accessToken, refreshToken,
                jwtProvider.getTokenType(), (int) jwtProvider.getRefreshTokenDuration());
    }
}
