package com.example.upload.tus.controller;

import com.example.upload.tus.service.HlsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@RequiredArgsConstructor
public class HlsController {

    private final HlsService hlsService;

    @ResponseBody
    @PostMapping("/convert/hls/{date}/{filename}")
    public String convertToHls(
            @PathVariable String date,
            @PathVariable String filename
    ) {
        hlsService.convertToHls(date, filename);
        return "success";
    }

    @GetMapping("/hls/play/{key}")
    public String play(
            Model model,
            @PathVariable String key
    ) {
        model.addAttribute("vodUrl", hlsService.getHlsPath(key));
        return "hls_player";
    }

    @ResponseBody
    @RequestMapping("/hls/{key}/{filename}")
    public ResponseEntity<InputStreamResource> getHlsFile(
            @PathVariable String key,
            @PathVariable String filename
    ) throws FileNotFoundException {
        File file = hlsService.getHlsFile(key, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }

}
