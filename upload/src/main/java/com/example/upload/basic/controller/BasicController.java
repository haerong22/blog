package com.example.upload.basic.controller;


import com.example.upload.basic.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final BasicService basicService;

    @GetMapping("/basic")
    public String basic() {
        return "basic";
    }

    @ResponseBody
    @PostMapping("/basic")
    public String saveFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("desc") String description) {
        basicService.saveFile(file);
        return "업로드 성공!! - 파일 이름: " + file.getOriginalFilename() + ", 파일 설명: " + description;
    }

}
