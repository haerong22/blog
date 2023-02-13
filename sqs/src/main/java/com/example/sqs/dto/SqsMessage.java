package com.example.sqs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SqsMessage {

    private String message;
    private User user;

    @Getter
    @Setter
    @ToString
    public static class User {
        private String userId;
        private String email;
    }
}
