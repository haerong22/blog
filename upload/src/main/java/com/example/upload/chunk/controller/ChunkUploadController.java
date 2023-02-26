package com.example.upload.chunk.controller;

import com.example.upload.chunk.service.ChunkUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChunkUploadController {

    private final ChunkUploadService chunkUploadService;

    @GetMapping("/chunk")
    public String chunkUploadPage() {
        return "chunk";
    }

    @ResponseBody
    @GetMapping("/chunk/upload/{key}")
    public ResponseEntity<?> getLastChunkNumber(@PathVariable String key) {
        return ResponseEntity.ok(chunkUploadService.getLastChunkNumber(key));
    }

    @ResponseBody
    @PostMapping("/chunk/upload/{key}")
    public ResponseEntity<String> chunkUpload(@RequestParam("chunk") MultipartFile file,
                                              @RequestParam("chunkNumber") int chunkNumber,
                                              @RequestParam("totalChunks") int totalChunks,
                                              @PathVariable String key) throws IOException {
        boolean isDone = chunkUploadService.chunkUpload(file, chunkNumber, totalChunks, key);

        return isDone ?
                ResponseEntity.ok("File uploaded successfully") :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
}
