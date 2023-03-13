package com.example.upload.tus.controller;

import com.example.upload.tus.service.ConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConvertController {

    private final ConvertService convertService;

    @ResponseBody
    @PostMapping("/convert/hls/{date}/{filename}")
    public String convertToHls(
            @PathVariable String date,
            @PathVariable String filename
    ) {
        convertService.convertToHls(date, filename);
        return "success";
    }

    @ResponseBody
    @PostMapping("/convert/resolutions/{date}/{filename}")
    public String convertToResolution(
            @PathVariable String date,
            @PathVariable String filename
    ) {
        convertService.convertResolutions(date, filename);
        return "success";
    }

}
