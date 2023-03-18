package com.example.upload.tus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class HlsServiceV2 {

    @Value("${tus.output.path.hls}")
    private String outputPath;

    public String getHlsPathV2(String key) {
        return "http://localhost:8080/v2/hls/" + key + "/master.m3u8";
    }

    public String getHlsPathV2(String key, String resolution) {
        return "http://localhost:8080/v2/hls/" + key + "/" + resolution + "/playlist.m3u8";
    }

    public File getHlsFileV2(String key, String resolution, String filename) {
        return new File(outputPath + "/" + key + "/"  + resolution + "/" + filename);
    }

    public File getHlsFileV2(String key, String filename) {
        return new File(outputPath + "/" + key + "/" + filename);
    }
}
