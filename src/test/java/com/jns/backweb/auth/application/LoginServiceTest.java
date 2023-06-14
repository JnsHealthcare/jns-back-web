package com.jns.backweb.auth.application;

import com.jns.backweb.auth.application.dto.RegisterRequest;
import com.jns.backweb.auth.exception.DuplicatedEmailException;
import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ActiveProfiles("test")
class LoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.ofSimpleMember("admin@gmail.com", "이라임"));
    }

    @Test
    @DisplayName("가입이 되어있지않은 사용자라면, 회원가입을 할 수 있다.")
    void register_test_success() {

        // given
        String name = "김라임";
        String email = "lime@gmail.com";
        String phoneNumber = "01012341234";
        LocalDate birthDate = LocalDate.of(2000, 8, 12);
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email, name, birthDate, phoneNumber, password);

        // when
        loginService.register(registerRequest);

        // then
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        assertThat(optionalMember.isPresent()).isTrue();

        Member member = optionalMember.get();
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getBirthDate()).isEqualTo(birthDate);
        assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(member.getPassword()).isNotEqualTo(password);

    }

    @Test
    @DisplayName("가입되어 있는 이메일로 회원가입을 하려고하면, 회원가입을 실패하고 예외가 발생한다.")
    void register_test_fail_duplicateEmail() {

        // given
        String name = "김라임";
        String email = "admin@gmail.com";
        String phoneNumber = "01012341234";
        LocalDate birthDate = LocalDate.of(2000, 8, 12);
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email, name, birthDate, phoneNumber, password);

        // when & then
        assertThatThrownBy(() ->loginService.register(registerRequest))
                .isInstanceOf(DuplicatedEmailException.class)
                .hasMessage(ErrorCodeAndMessage.DUPLICATED_EMAIL.getMessage());
    }

    @Test
    @DisplayName("이메일의 중복을 확인할 수 있다.")
    void checkAvailableEmail_test_success() {

        // given
        String newbieEmail = "newbie@gmail.com";


        // when
        loginService.checkAvailableEmail(newbieEmail);

        // then
        assertThat(memberRepository.existsByEmail(newbieEmail)).isFalse();

    }

    @Test
    @DisplayName("이미 사용중인 이메일이라면 예외를 발생시킨다.")
    void checkAvailableEmail_test_fail() {

        // given
        String newbieEmail = "newbie@gmail.com";

        // when
        assertThatThrownBy(() -> loginService.checkAvailableEmail(newbieEmail))
                .isInstanceOf(DuplicatedEmailException.class)
                .hasMessage(ErrorCodeAndMessage.DUPLICATED_EMAIL.getMessage());

    }
}
