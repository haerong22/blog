package com.example.email.service;

import com.example.email.dto.EmailSenderDto;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;

    private static final String TEMP_DIR = "./.temp";
    private static final String DEFAULT_EMAIL_ADDRESS = "admin@admin.com";
    private static final String DEFAULT_EMAIL_SENDER = "Admin";

    @Async
    public void send(
            EmailSenderDto dto
    ) {
        try {
            Session session = createProps();
            Message msg = new MimeMessage(session);

            Multipart content = new MimeMultipart();

            List<File> files = multipartToFile(dto.getFiles());

            MimeBodyPart htmlPart = getHtmlPart(dto);
            content.addBodyPart(htmlPart);

            for (File file : files) {
                MimeBodyPart filePart = getFilePart(file);
                content.addBodyPart(filePart);
            }

            if (dto.getFromEmail() == null) {
                dto.setFromEmail(DEFAULT_EMAIL_ADDRESS);
                dto.setFrom(DEFAULT_EMAIL_SENDER);
            }
            
            msg.setFrom(new InternetAddress(dto.getFromEmail(), dto.getFrom()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dto.getToEmail()));
            msg.setSubject(dto.getSubject());
            msg.setContent(content);

            Transport transport = session.getTransport();

            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            for (File file : files) {
                file.deleteOnExit();
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Session createProps() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(props);
    }


    private MimeBodyPart getHtmlPart(EmailSenderDto dto) throws MessagingException {
        MimeBodyPart htmlPart = new MimeBodyPart();

        Context context = new Context();
        context.setVariable("content", dto.getContent());

        String mailTemplate = templateEngine.process(dto.getTemplate(), context);

        htmlPart.setContent(mailTemplate, "text/html; charset=UTF-8"); // 메일 내용

        return htmlPart;
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

    private MimeBodyPart getFilePart(File file) throws IOException, MessagingException {
        MimeBodyPart filePart = new MimeBodyPart();

        FileDataSource fileDataSource = new FileDataSource(file);

        filePart.setDataHandler(new DataHandler(fileDataSource));
        filePart.setFileName(fileDataSource.getName());

        return filePart;
    }
}
