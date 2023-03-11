package com.example.upload.tus.service;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class HlsService {

    private final FFmpeg fFmpeg;
    private final FFprobe fFprobe;

    @Value("${tus.save.path}")
    private String savedPath;

    @Value("${tus.output.path}")
    private String outputPath;

    public void convertToHls(String date, String filename) {
        String path = savedPath + "/" + date + "/" + filename;
        File output = new File(outputPath + "/" + filename.split("\\.")[0]);

        if (!output.exists()) {
            output.mkdirs();
        }

        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(path)
                .overrideOutputFiles(true)
                .addOutput(output.getAbsolutePath() + "/master.m3u8")
                .setFormat("hls")
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-hls_segment_filename", output.getAbsolutePath() + "/master_%08d.ts")
                .done();

        executor.createJob(builder).run();
    }

    public String getHlsPath(String key) {
        return "http://localhost:8080/hls/" + key + "/master.m3u8";
    }

    public File getHlsFile(String key, String filename) {
        return new File(outputPath + "/" + key + "/" + filename);
    }
}
