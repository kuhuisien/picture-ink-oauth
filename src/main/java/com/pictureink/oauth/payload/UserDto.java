package com.pictureink.oauth.payload;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String picture;
}
