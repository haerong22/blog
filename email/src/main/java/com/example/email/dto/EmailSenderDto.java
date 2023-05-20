package com.example.email.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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

    public void jsonToMap() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.content = mapper.readValue((String) this.content, new TypeReference<HashMap<String,Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}