package com.pictureink.oauth.config;

import com.pictureink.oauth.util.Constant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${security.login.redirect-url}")
    private String loginSuccessUrl;

    @Value("${security.logout.redirect-url}")
    private String logoutSuccessUrl;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl(loginSuccessUrl, true))
                .logout(logout -> logout
                        .logoutUrl(Constant.LOGOUT_URL) // endpoint for logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // redirect after logout
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.sendRedirect(logoutSuccessUrl);
                        })
                        .invalidateHttpSession(true) // invalidate the session
                        .clearAuthentication(true) // clear authentication
                        .deleteCookies(Constant.COOKIE)
                );
        ;
        return http.build();
    }
}

