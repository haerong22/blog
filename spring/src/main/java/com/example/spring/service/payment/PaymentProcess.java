package com.example.spring.service.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcess {

    private final List<PaymentProcessor> paymentProcessors;

    public void payment(PaymentType paymentType) {

        PaymentProcessor paymentProcessor = paymentProcessors.stream()
                .filter(p -> p.isSupported(paymentType))
                .findFirst()
                .orElseThrow();

        paymentProcessor.payment();
    }
}
