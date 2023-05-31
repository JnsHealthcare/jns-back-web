package com.jns.backweb.auth.model;

import com.jns.backweb.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberInfo {

    private final String oauthId;
    private final String nickname;
    private final String email;
    private final String profileImage;


    public Member convertToSimpleMember() {
        return Member.ofSimpleMember(oauthId, nickname, email, profileImage);
    }


}
