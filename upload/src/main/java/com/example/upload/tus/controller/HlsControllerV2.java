package com.example.upload.tus.controller;

import com.example.upload.tus.service.HlsServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@RequiredArgsConstructor
public class HlsControllerV2 {

    private final HlsServiceV2 hlsServiceV2;

    @GetMapping("/v2/hls/play/{key}")
    public String play(
            Model model,
            @PathVariable String key
    ) {
        model.addAttribute("vodUrl", hlsServiceV2.getHlsPathV2(key));
        return "hls_player";
    }

    @GetMapping("/v2/hls/play/{resolution}/{key}")
    public String playWithResolution(
            Model model,
            @PathVariable String resolution,
            @PathVariable String key
    ) {
        model.addAttribute("vodUrl", hlsServiceV2.getHlsPathV2(key, resolution));
        return "hls_player";
    }

    @ResponseBody
    @RequestMapping("/v2/hls/{key}/{filename}")
    public ResponseEntity<InputStreamResource> getMaster(
            @PathVariable String key,
            @PathVariable String filename
    ) throws FileNotFoundException {
        File file = hlsServiceV2.getHlsFileV2(key, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }

    @ResponseBody
    @RequestMapping("/v2/hls/{key}/{resolution}/{filename}")
    public ResponseEntity<InputStreamResource> getPlaylist(
            @PathVariable String key,
            @PathVariable String resolution,
            @PathVariable String filename
    ) throws FileNotFoundException {
        File file = hlsServiceV2.getHlsFileV2(key, resolution, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }

}
