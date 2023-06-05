package com.example.upload.tus.service;

import com.example.upload.ffmpeg.FFmpegManager;
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
    private final FFmpegManager ffmpegManager;

    @Value("${tus.save.path}")
    private String savePath;

    public String tusUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);

            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                createFileV2(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());

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

//        ThumbnailExtractor.extract(file);
//        DurationExtractor.extract(file);

        ffmpegManager.getThumbnail(file.getAbsolutePath());
        ffmpegManager.getDuration(file.getAbsolutePath());
    }

    private void createFileV2(InputStream is, String filename) throws IOException {
        LocalDate today = LocalDate.now();

        String uploadedPath = savePath + "/" + today;

        String vodName = getVodName(filename);

        File file = new File(uploadedPath, vodName);

        FileUtils.copyInputStreamToFile(is, file);

        double duration = DurationExtractor.extract(file);

        for (long i = 0; i < duration; i++) {
            System.out.println("duration = " + duration);
            ThumbnailExtractor.extract(file, i);
        }

        ThumbnailMerger.merge(filename, (long) duration);
    }


    private String getVodName(String filename) {
        String[] split = filename.split("\\.");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + split[split.length - 1];
    }
}