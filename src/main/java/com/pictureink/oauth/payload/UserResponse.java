package com.pictureink.oauth.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse extends UserDto{
    String token;
}
