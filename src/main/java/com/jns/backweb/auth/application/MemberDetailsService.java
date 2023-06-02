package com.jns.backweb.auth.application;

import com.jns.backweb.auth.model.LoginMember;
import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;
import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new JnsWebApplicationException(ErrorCodeAndMessage.INVALID_REQUEST));

        return LoginMember.from(member);
    }

    public UserDetails loadMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new JnsWebApplicationException(ErrorCodeAndMessage.INVALID_REQUEST));

        return LoginMember.from(member);
    }
}
