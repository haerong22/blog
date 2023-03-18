package com.example.upload.tus.controller;

import com.example.upload.tus.service.ConvertServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConvertControllerV2 {

    private final ConvertServiceV2 convertServiceV2;

    @PostMapping("/v2/convert/hls/{date}/{filename}")
    public String convertToHls(
            @PathVariable String date,
            @PathVariable String filename
    ) {
        convertServiceV2.convertToHls(date, filename);
        return "success";
    }
}
