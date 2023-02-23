package com.example.upload.basic.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BasicService {
    public void saveFile(MultipartFile file) {

        if (!file.isEmpty()) {
            Path filepath = Paths.get("video", file.getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(filepath)) {
                os.write(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
