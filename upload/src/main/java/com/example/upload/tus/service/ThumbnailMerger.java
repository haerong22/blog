package com.example.upload.tus.service;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ThumbnailMerger {

    public static void merge(String filePath, String vodKey) {

        String path = filePath + "/" + vodKey;

        File directory = new File(path);

        try {
            int width = 256;
            int height = 144;

            int length = directory.listFiles().length;

            // 합쳐진 이미지를 저장할 BufferedImage 생성
            BufferedImage mergedImage = new BufferedImage(width * 5, height * 5, BufferedImage.TYPE_INT_RGB);

            // Graphics2D 객체를 사용하여 이미지를 합침
            Graphics2D g2d = mergedImage.createGraphics();
            g2d.setColor(Color.BLACK); // 배경 색상 설정
            g2d.fillRect(0, 0, width * 5, height * 5); // 배경 색상으로 이미지를 채움

            int num = 0;

            for (int i = 1; i <= length; i++) {
                int col = Math.max(0, i - (num * 25) - 1) % 5;
                int row = Math.max(0, i - (num * 25) - 1) / 5;
                int x = width * col;
                int y = height * row;
                System.out.println(i + " ==> (" + x + ", " + y + ")");

                File file = new File(path + "/" + vodKey + "_" + (i - 1) + ".png");
                BufferedImage image = ImageIO.read(file);

                g2d.drawImage(image, x, y, width, height, null);
                image.flush();

                if (i % 25 == 0 || i == length) {
                    // 합쳐진 이미지를 저장
                    File outputFile = new File(filePath, vodKey + "_" + num + ".png");
                    ImageIO.write(mergedImage, "png", outputFile);
                    num++;
                    g2d.setColor(Color.BLACK); // 배경 색상 설정
                    g2d.fillRect(0, 0, width * 5, height * 5); // 배경 색상으로 이미지를 채움
                    log.info("이미지 합치기 완료 ===> {}", outputFile.getPath());
                }
            }

            mergedImage.flush();
            g2d.dispose(); // Graphics2D 객체 해제
        } catch (IOException e) {
            log.error("이미지 합치기 실패 ===> ", e);
        }
    }
}
