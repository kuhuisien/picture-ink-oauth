package com.pictureink.oauth.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests/exception")
public class RestProcessingExceptionThrowingController {
    @SneakyThrows
    @GetMapping(value = "/bad-user-input")
    public @ResponseBody
    String find() {
        throw new Exception("global_exception_handler_test_bad_user_input");
    }
}
