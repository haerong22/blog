package com.example.spring.service.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WonPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean isSupported(PaymentType paymentType) {
        return paymentType.equals(PaymentType.WON);
    }

    @Override
    public void payment() {
        log.info("원화 결제 완료!");
    }
}
