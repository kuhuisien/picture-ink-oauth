package com.pictureink.oauth.service;

import com.pictureink.oauth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void givenExistingUser_whenCreateUserIfNotExists_returnFalse(){
        String mockEmail = "mock email";
        OAuth2User oAuth2User = new OAuth2User() {
            @Override
            public Map<String, Object> getAttributes() {
                return Map.of(
                        "email", mockEmail
                );
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        when(userRepository.existsById(mockEmail)).thenReturn(true);

        boolean result = userService.createUserIfNotExists(oAuth2User);

        assertFalse(result);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void givenNewUser_whenCreateUserIfNotExists_returnTrue(){
        String mockEmail = "mock email";
        OAuth2User oAuth2User = new OAuth2User() {
            @Override
            public Map<String, Object> getAttributes() {
                return Map.of(
                        "email", mockEmail
                );
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        when(userRepository.existsById(mockEmail)).thenReturn(false);

        boolean result = userService.createUserIfNotExists(oAuth2User);

        assertTrue(result);
        verify(userRepository, times(1)).save(any());
    }

}
