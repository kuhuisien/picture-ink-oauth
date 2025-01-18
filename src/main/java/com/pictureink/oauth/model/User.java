package com.pictureink.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @Field
    private Instant createdAt;

    @Field
    private Instant updatedAt;
}
