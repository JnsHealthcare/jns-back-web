package com.jns.backweb.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OauthClientRepository {

    private final Map<String, OauthClient> oauthClients;

    public OauthClient findOauthClient(String providerName) {
        return oauthClients.get(providerName);
    }
}
