package com.jns.backweb.auth;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;


@RequiredArgsConstructor
public enum OAuthAttribute {
    KAKAO("kakao", attributes -> {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return new MemberInfo(
                (String) attributes.get("sub"),
                (String) profile.get("nickname"),
                (String) profile.get("thumbnail_image_url"),
                (String) account.get("email"));
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberInfo> attributeToMemberInfo;

    public static MemberInfo toMemberInfo(String registrationId, Map<String, Object> attributes) {

        return Arrays.stream(values())
                .filter(provider -> provider.registrationId.equals(registrationId))
                .map(p -> p.attributeToMemberInfo.apply(attributes))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
