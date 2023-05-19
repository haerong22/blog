package com.example.email.service;

import com.example.email.dto.EmailSenderDto;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringEmailSender {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    private static final String TEMP_DIR = "./.temp";
    private static final String DEFAULT_EMAIL_ADDRESS = "admin@admin.com";
    private static final String DEFAULT_EMAIL_SENDER = "Admin";

    @Async
    public void send(
            EmailSenderDto dto
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            List<File> files = multipartToFile(dto.getFiles());

            for (File file : files) {
                helper.addAttachment(file.getName(), file);
            }

            if (dto.getFromEmail() == null) {
                dto.setFromEmail(DEFAULT_EMAIL_ADDRESS);
                dto.setFrom(DEFAULT_EMAIL_SENDER);
            }

            helper.setFrom(dto.getFromEmail(), dto.getFrom());
            helper.setTo(dto.getToEmail());
            helper.setSubject(dto.getSubject());
            helper.setText(getHtmlPart(dto), true);

            mailSender.send(message);
            for (File file : files) {
                file.deleteOnExit();
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
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
