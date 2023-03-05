package com.example.upload.ffmpeg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class FFmpegManager {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;

    private static final String THUMBNAIL_EXTENSION = ".png";
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/static/images/default-thumbnail.png";


    public void getThumbnail(String sourcePath) {
        final String outputPath = sourcePath.split("\\.")[0] + THUMBNAIL_EXTENSION;

        try {
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(sourcePath)
                    .overrideOutputFiles(true)
                    .addOutput(outputPath)
                    .setFormat("image2")
                    .setFrames(1)
                    .setVideoFrameRate(1)
                    .done();

            ffmpeg.run(builder);
        } catch (Exception e) {
            File thumbnail = new File(outputPath);
            File defaultImage = new File(DEFAULT_IMAGE_PATH);

            try {
                FileUtils.copyFile(defaultImage, thumbnail);
            } catch (Exception ex) {
                log.error("Thumbnail Extract Failed => {}", sourcePath, e);
            }
        }
    }

    public void getDuration(String sourcePath) throws IOException {
        Path videoPath = Paths.get(sourcePath);

        FFmpegProbeResult probeResult = ffprobe.probe(videoPath.toString());

        FFmpegStream videoStream = probeResult.getStreams().get(0);
        double durationInSeconds = videoStream.duration;

        log.info("Video length: {} seconds", durationInSeconds);
    }
}
