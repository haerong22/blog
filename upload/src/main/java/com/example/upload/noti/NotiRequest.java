package com.example.upload.noti;

import lombok.Data;

@Data
public class NotiRequest {

    private String path;
    private String dirname;
    private String basename;
    private String token;
}
