package com.jns.backweb.auth.model;

import com.jns.backweb.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class MemberInfo {

    private final String email;
    private final String name;


    public Member convertToSimpleMember() {
        return Member.ofSimpleMember(email, name);
    }


}
