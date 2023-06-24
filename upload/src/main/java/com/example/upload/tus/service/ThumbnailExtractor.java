package com.example.upload.tus.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ThumbnailExtractor {

    private static final String EXTENSION = "png";
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/static/images/default-thumbnail.png";

    public static String extract(File source) throws IOException {
        File thumbnail = new File(source.getParent(), source.getName().split("\\.")[0] + "." + EXTENSION);

        try {
            FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

            frameGrab.seekToSecondPrecise(0);

            Picture picture = frameGrab.getNativeFrame();

            BufferedImage bi = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bi, EXTENSION, thumbnail);

        } catch (Exception e) {
            File defaultImage = new File(DEFAULT_IMAGE_PATH);

            try {
                FileUtils.copyFile(defaultImage, thumbnail);
            } catch (Exception ex) {
                log.info("Thumbnail Extract Failed => {}", source.getPath(), e);
            }
        }

        return thumbnail.getName();
    }

    public static String extract(File source, long second, long duration) throws IOException {
        String filename = source.getName().split("\\.")[0];
        File thumbnail = new File(source.getParent() + "/" + filename, filename + "_" + second + "." + EXTENSION);

        if (!thumbnail.getParentFile().exists()) {
            thumbnail.getParentFile().mkdirs();
        }

        try {
            int width = 256;
            int height = 144;

            FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

            frameGrab.seekToSecondPrecise(second);

            Picture picture = frameGrab.getNativeFrame();

            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            BufferedImage bi = AWTUtil.toBufferedImage(picture);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(bi, 0, 0, width, height, null);
            ImageIO.write(resized, EXTENSION, thumbnail);

            resized.flush();
            bi.flush();
        } catch (Exception e) {
            e.printStackTrace();
            File defaultImage = new File(DEFAULT_IMAGE_PATH);

            try {
                FileUtils.copyFile(defaultImage, thumbnail);
            } catch (Exception ex) {
                log.info("Thumbnail Extract Failed => {}", source.getPath(), e);
            }
        }

        if (second == duration) {
            ThumbnailMerger.merge(source.getParent(), filename);
        }

        return thumbnail.getName();
    }
}
