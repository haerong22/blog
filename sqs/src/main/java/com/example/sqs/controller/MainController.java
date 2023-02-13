package com.example.sqs.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.sqs.aws.sqs.AwsSqsSender;
import com.example.sqs.dto.SqsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

    private final AwsSqsSender awsSqsSender;

    @PostMapping("/send")
    public String send(@RequestBody SqsMessage message) throws JsonProcessingException {
        SendMessageResult result = awsSqsSender.sendMessage(message);
        log.info("result ==> {}", result);
        return "success";
    }
}