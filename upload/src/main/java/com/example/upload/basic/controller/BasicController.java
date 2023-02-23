package com.example.upload.basic.controller;


import com.example.upload.basic.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final BasicService basicService;

    @GetMapping("/basic")
    public String basic() {
        return "basic";
    }

    @PostMapping("/basic")
    public String saveFile(Model model, @RequestParam MultipartFile file) {
        basicService.saveFile(file);
        model.addAttribute("result", "업로드 성공!!");
        return "basic";
    }

}
