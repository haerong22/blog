package com.example.upload.tus.controller;

import com.example.upload.tus.service.TusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TusController {

    private final TusService tusService;

    @GetMapping("/tus")
    public String tusUploadPage() {
        return "tus";
    }

    @ResponseBody
    @RequestMapping(value = {"/tus/upload", "/tus/upload/**"})
    public ResponseEntity<String> tusUpload(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(tusService.tusUpload(request, response));
    }

}