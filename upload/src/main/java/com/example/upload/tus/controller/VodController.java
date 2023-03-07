package com.example.upload.tus.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class VodController {

    @Value("${tus.save.path}")
    private String savedPath;

    @GetMapping("/play/{date}/{key}")
    public String play(
            Model model,
            @PathVariable String date,
            @PathVariable String key
    ) {
        String path = "http://localhost:8080/vod/" + date + "/" + key;
        model.addAttribute("videoUrl", path + ".mp4");
        model.addAttribute("thumbnailUrl", path + ".png");

        String chunkPath = "http://localhost:8080/vod/chunk/" + date + "/" + key;
        model.addAttribute("chunkVideoUrl", chunkPath + ".mp4");
        model.addAttribute("chunkThumbnailUrl", path + ".png");

        return "player";
    }

    @ResponseBody
    @GetMapping("/vod/{date}/{filename}")
    public ResponseEntity<Resource> resource(
            @PathVariable String date,
            @PathVariable String filename
    ) throws IOException {
        String path = savedPath + "/" + date + "/" + filename;
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @ResponseBody
    @GetMapping("/vod/chunk/{date}/{filename}")
    public ResponseEntity<ResourceRegion> chunkResource(
            @RequestHeader HttpHeaders headers,
            @PathVariable String date,
            @PathVariable String filename
    ) throws IOException {

        String path = savedPath + "/" + date + "/" + filename;
        Resource resource = new FileSystemResource(path);

        long chunkSize = 1024 * 1024;
        long contentLength = resource.contentLength();


        HttpRange httpRange = headers.getRange().stream().findFirst()
                .orElse(HttpRange.createByteRange(0, contentLength - 1));

        long rangeLength = calculateRangeLength(httpRange, contentLength, chunkSize);
        ResourceRegion region = new ResourceRegion(resource, httpRange.getRangeStart(contentLength), rangeLength);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Accept-Ranges", "bytes")
                .eTag(path)
                .body(region);
    }

    private long calculateRangeLength(HttpRange httpRange, long contentLength, long chunkSize) {
        long start = httpRange.getRangeStart(contentLength);
        long end = httpRange.getRangeEnd(contentLength);
        return Long.min(chunkSize, end - start + 1);
    }
}
