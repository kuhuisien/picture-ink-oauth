package com.pictureink.oauth.service;

import com.pictureink.oauth.model.User;
import com.pictureink.oauth.repository.UserRepository;
import com.pictureink.oauth.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUserIfNotExists(OAuth2User oAuth2User) {
        Map<String, Object> userAttributes = oAuth2User.getAttributes();
        String id  = (String) userAttributes.get(Constant.EMAIL);

        if (userRepository.existsById(id)) {
            return false;
        }

        userRepository.save(new User(id, Instant.now(), Instant.now()));
        return true;
    }
}
