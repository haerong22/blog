package com.example.upload.tus.service;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ThumbnailMerger {

    public static void merge(String filePath, String vodName, long duration) {

        String path = filePath + "/" + vodName;

        System.out.println("path = " + path);

        try {
            // 첫 번째 이미지 파일을 기준으로 너비와 높이를 가져옴
            BufferedImage baseImage = ImageIO.read(new File(path + "/"  + vodName + "_0.png"));
            int width = baseImage.getWidth();
            int height = baseImage.getHeight();

            // 합쳐진 이미지를 저장할 BufferedImage 생성
            BufferedImage mergedImage = new BufferedImage(width * 3, height, BufferedImage.TYPE_INT_RGB);

            // Graphics2D 객체를 사용하여 이미지를 합침
            Graphics2D g2d = mergedImage.createGraphics();
            g2d.setColor(java.awt.Color.WHITE); // 배경 색상 설정
            g2d.fillRect(0, 0, width, height); // 배경 색상으로 이미지를 채움

            int x = 0;
            for (long i = 0; i < duration; i++) {
                String imagePath = path + "/" + vodName + "_" + i + ".png";
                BufferedImage image = ImageIO.read(new File(imagePath));
                g2d.drawImage(image, x, 0, null);
                x += image.getWidth();
            }

            g2d.dispose(); // Graphics2D 객체 해제

            // 합쳐진 이미지를 저장
            File outputFile = new File(path + "_m.png");
            System.out.println("outputFile = " + outputFile);
            ImageIO.write(mergedImage, "png", outputFile);

            log.info("이미지 합치기가 완료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
