package com.example.jwt.jwt;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberInfo {

    private Long id;
    private String username;
}
