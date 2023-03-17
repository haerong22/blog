package com.example.upload.tus.controller;

import com.example.upload.tus.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/vod/approve/{date}/{filename}")
    public String approve(
            @PathVariable String date,
            @PathVariable String filename
    ) {
        adminService.approve(date, filename);
        return "success";
    }
}
