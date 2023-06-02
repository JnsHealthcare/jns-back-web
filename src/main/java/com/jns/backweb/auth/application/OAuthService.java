package com.jns.backweb.auth.application;

import com.jns.backweb.auth.model.LoginMember;
import com.jns.backweb.auth.model.MemberInfo;
import com.jns.backweb.auth.model.OAuthAttribute;
import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        MemberInfo memberInfo = OAuthAttribute.toMemberInfo(registrationId, attributes);

        Member member = findOrSave(memberInfo);

        return LoginMember.of(member, attributes);
    }

    private Member findOrSave(MemberInfo memberInfo) {
        return memberRepository.findByEmail(memberInfo.getEmail())
                .orElseGet(() -> memberRepository.save(memberInfo.convertToSimpleMember()));
    }

}
