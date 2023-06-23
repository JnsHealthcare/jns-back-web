package com.jns.backweb.auth.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jns.backweb.auth.config.OauthProperties;
import com.jns.backweb.auth.exception.OauthException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "kakao")
@Getter
@Slf4j
public class KakaoOauthClient implements OauthClient {

    private static final String KAKAO_PROVIDER_NAME = "kakao";
    private final OauthProperties.ProviderProperties properties;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public KakaoOauthClient(OauthProperties oauthProperties, HttpClient httpClient, ObjectMapper objectMapper) {
        System.out.println(oauthProperties == null);
        OauthProperties.ProviderProperties providerProperties = oauthProperties.getProviderProperties(KAKAO_PROVIDER_NAME);
        System.out.println(providerProperties == null);
        this.properties = oauthProperties.getProviderProperties(KAKAO_PROVIDER_NAME);
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> getOauthMemberInfo(String code) {
        Map<String, Object> userInfos;
        try {
            Map<String, Object> tokenInfo = getToken(code);
            userInfos = getUserInfo(tokenInfo);
        } catch (IOException | InterruptedException e) {
            throw new OauthException(e.getCause());
        }

        return userInfos;
    }


    private Map<String, Object> getToken(String code) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        log.debug(properties.getTokenType());
        params.put("grant_type", properties.getGrantType());
        params.put("client_id", properties.getClientId());
        params.put("redirect_uri", URLEncoder.encode(properties.getRedirectUri(), StandardCharsets.UTF_8));
        params.put("code", code);
        params.put("client_secret", properties.getClientSecret());

        String formData = params.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.getTokenUri()))
                .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();


        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    public Map<String, Object> getUserInfo(Map<String, Object> tokenInfo) throws IOException, InterruptedException {
        String tokenType = (String) tokenInfo.get("token_type");
        String accessToken = (String) tokenInfo.get("access_token");

        String authHeader = tokenType + " " + accessToken;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.getUserInfoUri()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        return objectMapper.readValue(response.body(), new TypeReference<>() {});

    }
}
