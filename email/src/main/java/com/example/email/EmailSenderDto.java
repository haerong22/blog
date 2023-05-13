package com.example.email;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class EmailSenderDto {
    private String subject;
    private Object content;
    private List<MultipartFile> files;
    private String toEmail;

    private String fromEmail;
    private String from;
    private String template;


}