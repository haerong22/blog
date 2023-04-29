package com.example.upload.noti;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

@RestController
public class NotiController {


    @RequestMapping("/test")
    public String call(HttpServletRequest request) {
        System.out.println("servletRequest.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            System.out.println(s);
        }

        //body에 있는 제이슨을 받은 객체
        String bodyJson = "";

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        //한줄씩 담을 변수
        String line = "";

        try {
            //body내용 inputstream에 담는다.
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                //더 읽을 라인이 없을때까지 계속
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }else {
                System.out.println("음슴");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bodyJson = stringBuilder.toString();

        System.out.println("bodyJson = " + bodyJson);
        return "test!!!1";
    }

    @RequestMapping("/audio")
    public String audio(HttpServletRequest servletRequest, NotiRequest request) {

        System.out.println("servletRequest.getRequestURI() = " + servletRequest.getRequestURI());
        System.out.println("servletRequest.getHeaderNames() = " + servletRequest.getHeaderNames());

        System.out.println("request = " + request);
        return "success";
    }
}
