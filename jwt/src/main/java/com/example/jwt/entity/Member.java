package com.example.jwt.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

    private Long id;
    private String username;
    private String password;

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
