package com.example.upload.tus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.Progress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final FFmpeg fFmpeg;
    private final FFprobe fFprobe;

    @Value("${tus.save.path}")
    private String savedPath;

    @Value("${tus.output.path.hls}")
    private String hlsOutputPath;

    public void approve(String date, String filename) {
        convertToHls(date, filename);
    }

    private void convertToHls(String date, String filename) {
        Path input = Paths.get(savedPath + "/" + date + "/" + filename);

        File prefix = new File(hlsOutputPath + "/" + filename.split("\\.")[0]);
        File _1080 = new File(prefix, "1080");
        File _720 = new File(prefix, "720");
        File _480 = new File(prefix, "480");

        if (!_1080.exists()) _1080.mkdirs();
        if (!_720.exists()) _720.mkdirs();
        if (!_480.exists()) _480.mkdirs();

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(input.toAbsolutePath().toString())
                .addOutput(prefix.getAbsolutePath() + "/output.m3u8")
                .addExtraArgs("-hls_playlist_type", "vod")
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-var_stream_map", "v:0,name:720 v:1,name:480")
                .addExtraArgs("-hls_segment_filename", prefix.getAbsolutePath() + "/%v/file_%03d.ts")
                .addExtraArgs("-b:v:0", "5000k")
                .addExtraArgs("-b:v:1", "2500k")
                .addExtraArgs("-b:a:0", "64k")
                .addExtraArgs("-b:a:1", "32k")
                .addExtraArgs("-map", "0:v")
                .addExtraArgs("-map", "0:v")
//                .addExtraArgs("-map", "0:a?")
//                .addExtraArgs("-map", "0:a?")
                .done();

        run(builder);
    }

    private void run(FFmpegBuilder builder) {
        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);

        executor
                .createJob(builder, progress -> {
                    log.info("progress ==> {}", progress);
                    if (progress.status.equals(Progress.Status.END)) {
                        log.info("================================= JOB FINISHED =================================");
                    }
                })
                .run();
    }
}
