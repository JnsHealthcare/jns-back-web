package com.jns.backweb.auth;

import com.jns.backweb.member.domain.Member;
import com.jns.backweb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService = new DefaultOAuth2UserService();
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        MemberInfo memberInfo = OAuthAttribute.toMemberInfo(registrationId, attributes);

        Member member = findOrSave(memberInfo);

        return new LoginMember(member.getId(), member.getNickname(), member.getEmail(), member.getProfileImage(), oAuth2User);
    }

    private Member findOrSave(MemberInfo memberInfo) {
        return memberRepository.findByOauthId(memberInfo.getOauthId())
                .orElseGet(() -> memberRepository.save(memberInfo.convertToSimpleMember()));
    }

}
