package com.likelion.boomarble.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.dto.UserSignUpRequestDto;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import com.likelion.boomarble.domain.user.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OAuthService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public OAuthService(Environment env, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.env = env;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String socialLogin(String code, String registrationId) throws Exception {
        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        System.out.println("userResourceNode = " + userResourceNode);

        String email = userResourceNode.get("email").asText();
        String id = userResourceNode.get("id").asText();
        String name = userResourceNode.get("name").asText();
        System.out.println("email = " + email);
        System.out.println("id = " + id);
        System.out.println("name = " + name);

        String token;
        if (userRepository.findByEmail(email).isPresent()) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("email", email);
            userMap.put("name", name);
            token = login(userMap);
            System.out.println("jwt token" + token);
        } else {
            UserSignUpRequestDto requestDto = new UserSignUpRequestDto();
            requestDto.setEmail(email);
            Long result = signUp(requestDto);
            System.out.println("sign up success, id= " + result);

            // signup 후 바로 로그인
            Map<String, String> userMap = new HashMap<>();
            userMap.put("email", email);
            userMap.put("name", name);
            token = login(userMap);
            System.out.println("jwt token" + token);
        }
        return token;
    }

    public Long signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        User user = userRepository.save(requestDto.toEntity());

        return user.getId();
    }

    public String login(Map<String, String> users) {
        User user = userRepository.findByEmail(users.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email입니다."));

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return jwtTokenProvider.createToken(user.getEmail(), roles);
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        System.out.println("accesstoken : " + accessTokenNode.get("access_token").asText());
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2." + registrationId + ".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }




}
