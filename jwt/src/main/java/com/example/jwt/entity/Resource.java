package com.example.jwt.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Resource {

    private Long id;
    private String method;
    private String pattern;
    private boolean required;
}
