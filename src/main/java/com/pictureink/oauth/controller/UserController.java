package com.pictureink.oauth.controller;

import com.pictureink.oauth.payload.UserResponse;
import com.pictureink.oauth.util.JwtTokenUtil;
import com.pictureink.oauth.service.UserService;
import com.pictureink.oauth.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal OAuth2User principal) {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String token = jwtTokenUtil.generateToken(authentication);

        userService.createUserIfNotExists(principal);

        UserResponse userResponse = UserUtil.constructGetUserResponse(principal, token);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
