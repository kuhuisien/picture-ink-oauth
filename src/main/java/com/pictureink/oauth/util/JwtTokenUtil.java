package com.pictureink.oauth.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    public String generateToken(Authentication authentication) {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) oauth2Token.getPrincipal();
        return oidcUser.getIdToken().getTokenValue();
    }
}
