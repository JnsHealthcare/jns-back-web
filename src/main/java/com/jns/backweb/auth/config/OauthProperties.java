package com.jns.backweb.auth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "oauth")
@Getter
public class OauthProperties {

    private final Map<String, ProviderProperties> provider = new HashMap<>();

    public ProviderProperties getProviderProperties(String key) {
        return provider.get(key);
    }

    @ConstructorBinding
    @RequiredArgsConstructor
    @Getter
    public static class ProviderProperties {

        private final String grantType;
        private final String clientId;
        private final String clientSecret;
        private final String tokenUri;
        private final String userInfoUri;
        private final Set<String> scope;
        private final String tokenType;
        private final String redirectUri;



    }
}
