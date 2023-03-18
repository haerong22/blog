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
public class ConvertServiceV2 {

    private final FFmpeg fFmpeg;
    private final FFprobe fFprobe;

    @Value("${tus.save.path}")
    private String savedPath;

    @Value("${tus.output.path.hls}")
    private String hlsOutputPath;

    public void convertToHls(String date, String filename) {

        // 입력 파일 경로 설정
        Path inputFilePath = Paths.get(savedPath + "/" + date + "/" + filename);

        // 출력 폴더 경로 설정
        Path outputFolderPath = Paths.get(hlsOutputPath + "/" + filename.split("\\.")[0]);

        File prefix = outputFolderPath.toFile();
        File _1080 = new File(prefix, "1080");
        File _720 = new File(prefix, "720");
        File _480 = new File(prefix, "480");

        if (!_1080.exists()) _1080.mkdirs();
        if (!_720.exists()) _720.mkdirs();
        if (!_480.exists()) _480.mkdirs();

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFilePath.toAbsolutePath().toString())
                .addExtraArgs("-y")
                .addOutput(outputFolderPath.toAbsolutePath() + "/%v/playlist.m3u8")
                .setFormat("hls")
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-hls_segment_filename", outputFolderPath.toAbsolutePath() + "/%v/output_%03d.ts")
                .addExtraArgs("-master_pl_name", "master.m3u8")
                .addExtraArgs("-map", "0:v")
                .addExtraArgs("-map", "0:v")
                .addExtraArgs("-map", "0:v")
                .addExtraArgs("-var_stream_map", "v:0,name:1080 v:1,name:720 v:2,name:480")

                .addExtraArgs("-b:v:0", "5000k")
                .addExtraArgs("-maxrate:v:0", "5000k")
                .addExtraArgs("-bufsize:v:0", "10000k")
                .addExtraArgs("-s:v:0", "1920x1080")
                .addExtraArgs("-crf:v:0", "15")
                .addExtraArgs("-b:a:0", "128k")

                .addExtraArgs("-b:v:1", "2500k")
                .addExtraArgs("-maxrate:v:1", "2500k")
                .addExtraArgs("-bufsize:v:1", "5000k")
                .addExtraArgs("-s:v:1", "1280x720")
                .addExtraArgs("-crf:v:1", "22")
                .addExtraArgs("-b:a:1", "96k")

                .addExtraArgs("-b:v:2", "1000k")
                .addExtraArgs("-maxrate:v:2", "1000k")
                .addExtraArgs("-bufsize:v:2", "2000k")
                .addExtraArgs("-s:v:2", "854x480")
                .addExtraArgs("-crf:v:2", "28")
                .addExtraArgs("-b:a:2", "64k")
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
