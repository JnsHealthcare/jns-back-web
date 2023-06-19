package com.jns.backweb.auth.application;

import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.exception.DuplicatedEmailException;
import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    public LoginSuccessResult passwordLogin() {

        // login
        // token return
        // refresh token set cookie

        return null;
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
}
