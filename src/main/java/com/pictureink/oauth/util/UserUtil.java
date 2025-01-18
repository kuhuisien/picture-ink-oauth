package com.pictureink.oauth.util;

import com.pictureink.oauth.payload.UserResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class UserUtil {

    public static UserResponse constructGetUserResponse(OAuth2User oAuth2User, String token) {
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        String email = (String) userAttributes.get(Constant.EMAIL);
        String username = (String) userAttributes.get(Constant.USERNAME);
        String picture = (String) userAttributes.get(Constant.PICTURE);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(email);
        userResponse.setUsername(username);
        userResponse.setPicture(picture);
        userResponse.setToken(token);

        return userResponse;
    }
}
