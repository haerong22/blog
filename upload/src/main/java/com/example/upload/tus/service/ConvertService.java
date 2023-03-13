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
public class ConvertService {

    private final FFmpeg fFmpeg;
    private final FFprobe fFprobe;

    @Value("${tus.save.path}")
    private String savedPath;

    @Value("${tus.output.path.hls}")
    private String hlsOutputPath;

    @Value("${tus.output.path.mp4}")
    private String mp4OutputPath;

    public void convertToHls(String date, String filename) {
        String path = savedPath + "/" + date + "/" + filename;
        File output = new File(hlsOutputPath + "/" + filename.split("\\.")[0]);

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

    public void convertResolutions(String date, String filename) {
        String path = savedPath + "/" + date + "/" + filename;
        String key = filename.split("\\.")[0];
        File output = new File(mp4OutputPath + "/" + key);

        if (!output.exists()) {
            output.mkdirs();
        }

        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(path)
                .overrideOutputFiles(true)
                .addOutput(output.getAbsolutePath() + "/" + key + "_1080.mp4")
                .setVideoResolution(1920, 1080)
                .setVideoCodec("libx264")
                .setAudioCodec("copy")
                .setFormat("mp4")
                .done()
                .addOutput(output.getAbsolutePath() + "/" + key + "_720.mp4")
                .setVideoResolution(1280, 720)
                .setVideoCodec("libx264")
                .setAudioCodec("copy")
                .setFormat("mp4")
                .done()
                .addOutput(output.getAbsolutePath() + "/" + key + "_480.mp4")
                .setVideoResolution(720, 480)
                .setVideoCodec("libx264")
                .setAudioCodec("copy")
                .setFormat("mp4")
                .done();

        executor.createJob(builder).run();
    }
}
