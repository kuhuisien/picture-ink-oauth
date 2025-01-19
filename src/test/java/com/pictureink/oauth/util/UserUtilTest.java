package com.pictureink.oauth.util;

import com.pictureink.oauth.payload.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class UserUtilTest {

    @Test
    public void test() {
        String mockEmail = "mock email";
        String mockName = "mock name";
        String mockPicture = "mock picture";
        String mockToken = "mock token";
        OAuth2User oAuth2User = new OAuth2User() {
            @Override
            public Map<String, Object> getAttributes() {
                return Map.of("email", mockEmail, "picture", mockPicture, "given_name", mockName);
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
        UserResponse userResponse = UserUtil.constructGetUserResponse(oAuth2User, mockToken);

        Assertions.assertThat(userResponse.getToken()).isEqualTo(mockToken);
        Assertions.assertThat(userResponse.getEmail()).isEqualTo(mockEmail);
        Assertions.assertThat(userResponse.getPicture()).isEqualTo(mockPicture);
        Assertions.assertThat(userResponse.getUsername()).isEqualTo(mockName);
    }
}
