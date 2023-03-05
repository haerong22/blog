package com.example.upload.tus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TusService {

    private final TusFileUploadService tusFileUploadService;

    @Value("${tus.save.path}")
    private String savePath;

    public String tusUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);

            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());

                tusFileUploadService.deleteUpload(request.getRequestURI());

                return "success";
            }

            return null;
        } catch (IOException | TusException e) {
            log.error("exception was occurred. message={}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

    private void createFile(InputStream is, String filename) throws IOException {
        LocalDate today = LocalDate.now();

        String uploadedPath = savePath + "/" + today;

        String vodName = getVodName(filename);

        File file = new File(uploadedPath, vodName);

        FileUtils.copyInputStreamToFile(is, file);

        ThumbnailExtractor.extract(file);
    }

    private String getVodName(String filename) {
        String[] split = filename.split("\\.");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + split[split.length - 1];
    }
}