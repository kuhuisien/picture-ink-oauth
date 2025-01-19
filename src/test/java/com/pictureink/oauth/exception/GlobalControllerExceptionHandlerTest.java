package com.pictureink.oauth.exception;

import com.pictureink.oauth.controller.RestProcessingExceptionThrowingController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GlobalControllerExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private RestProcessingExceptionThrowingController restProcessingExceptionThrowingController;

    /**
     * Bypass security, optional step.
     */
    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public static class TestSecurityConfig {
        @Bean
        protected SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests.anyRequest().permitAll());
            return http.build();
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(restProcessingExceptionThrowingController)     // instantiate controller.
                .setControllerAdvice(new GlobalExceptionHandler())   // bind with controller advice.
                .build();
    }

    @Test
    public void testExceptionThrowingControllerExists() {
        assertThat(restProcessingExceptionThrowingController).isNotNull();
    }

    /**
     * Tests that exception is handled via global exception handler.
     *
     * @throws Exception
     */
    @Test
    public void testHandleBadUserInputException() throws Exception {
        this.mockMvc.perform(get("/tests/exception/bad-user-input"))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> {
                    result.getResolvedException();
                    Assertions.assertTrue(true);
                })
                .andExpect(result -> Assertions.assertEquals("global_exception_handler_test_bad_user_input",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
