package com.example.email.service;

import com.example.email.dto.EmailSenderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringEmailSender {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    private static final String TEMP_DIR = "./.temp";
    private static final String DEFAULT_EMAIL_ADDRESS = "admin@admin.com";
    private static final String DEFAULT_EMAIL_SENDER = "Admin";
    private static final String DEFAULT_EMAIL_TEMPLATE = "default_template";
    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT;

    @Async
    public void send(
            EmailSenderDto dto
    ) {
        init(dto);

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            List<File> files = multipartToFile(dto.getFiles());

            for (File file : files) {
                helper.addAttachment(file.getName(), file);
            }

            helper.setFrom(dto.getFromEmail(), dto.getFrom());
            helper.setTo(dto.getToEmail());
            helper.setSubject(dto.getSubject());

            switch (dto.getContentType()) {
                case HTML: {
                    helper.setText(String.valueOf(dto.getContent()), true);
                    break;
                }
                case JSON: {
                    helper.setText(getHtmlPart(dto), true);
                    break;
                }
                case TEXT: {
                    helper.setText(String.valueOf(dto.getContent()), false);
                    break;
                }
            }

            mailSender.send(message);

            for (File file : files) {
                file.delete();
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init(EmailSenderDto dto) {
        if (Objects.isNull(dto.getFromEmail())) {
            dto.setFromEmail(DEFAULT_EMAIL_ADDRESS);
        }

        if (Objects.isNull(dto.getFrom())) {
            dto.setFrom(DEFAULT_EMAIL_SENDER);
        }

        if (Objects.isNull(dto.getContentType())) {
            dto.setContentType(DEFAULT_CONTENT_TYPE);
        }

        if (Objects.isNull(dto.getTemplate())) {
            dto.setTemplate(DEFAULT_EMAIL_TEMPLATE);
        }

        if (dto.getContentType().equals(ContentType.JSON)) {
            try {
                dto.setContent(
                        objectMapper.readValue((String) dto.getContent(), new TypeReference<HashMap<String, Object>>() {})
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getHtmlPart(EmailSenderDto dto) throws MessagingException {
        Context context = new Context();
        context.setVariable("content", dto.getContent());
        return templateEngine.process(dto.getTemplate(), context);
    }

    private List<File> multipartToFile(List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();

        if (multipartFiles != null && multipartFiles.size() != 0) {

            File folder = new File(TEMP_DIR);

            if (!folder.exists()) {
                folder.mkdir();
            }

            for (MultipartFile multipartFile : multipartFiles) {
                File file = new File(TEMP_DIR + "/" + Objects.requireNonNull(multipartFile.getOriginalFilename()));
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(multipartFile.getBytes());
                fos.close();
                files.add(file);
            }

        }

        return files;
    }
}
