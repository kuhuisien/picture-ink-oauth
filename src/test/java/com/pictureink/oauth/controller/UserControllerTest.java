package com.pictureink.oauth.controller;

import com.pictureink.oauth.service.UserService;
import com.pictureink.oauth.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @MockitoBean
    private UserService userService;

    private OAuth2AuthenticationToken oAuth2AuthenticationToken;

    private final String MOCK_EMAIL = "johndoe@example.com";
    private final String MOCK_USERNAME = "John";
    private final String MOCK_PICTURE = "mock picture";

    @BeforeEach
    void setUp() {
        // Create a mock OAuth2User
        Map<String, Object> attributes = Map.of("name", "John Doe", "email", MOCK_EMAIL, "given_name", MOCK_USERNAME, "picture", MOCK_PICTURE);

        OAuth2User principal = new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "name"
        );

        // Create an OAuth2AuthenticationToken
        oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
                principal,
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                "client-id"
        );
    }

    @Test
    void givenUserIsAuthenticated_whenGetUser_thenReturnUserDetail() throws Exception {
        String mockToken = "mock token";
        when(jwtTokenUtil.generateToken(any())).thenReturn(mockToken);

        // Perform GET request with the mocked authentication
        mockMvc.perform(get("/user").with(authentication(oAuth2AuthenticationToken))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(MOCK_EMAIL))
                .andExpect(jsonPath("$.username").value(MOCK_USERNAME))
                .andExpect(jsonPath("$.picture").value(MOCK_PICTURE));

        verify(jwtTokenUtil, times(1)).generateToken(any());
        verify(userService, times(1)).createUserIfNotExists(any(OAuth2User.class));
    }

    @Test
    void givenUserIsNotAuthenticated_whenGetUser_thenRedirect() throws Exception {
        String mockToken = "mock-token";
        when(jwtTokenUtil.generateToken(any())).thenReturn(mockToken);

        // Perform GET request with the mocked authentication
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection());

        verify(jwtTokenUtil, times(0)).generateToken(any());
        verify(userService, times(0)).createUserIfNotExists(any(OAuth2User.class));
    }
}