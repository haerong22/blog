package com.example.upload.tus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class HlsService {

    @Value("${tus.output.path.hls}")
    private String outputPath;

    public String getHlsPath(String key) {
        return "http://localhost:8080/hls/" + key + "/master.m3u8";
    }

    public File getHlsFile(String key, String filename) {
        return new File(outputPath + "/" + key + "/" + filename);
    }
}
