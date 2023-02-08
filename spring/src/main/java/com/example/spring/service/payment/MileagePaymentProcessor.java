package com.example.spring.service.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MileagePaymentProcessor implements PaymentProcessor {
    @Override
    public boolean isSupported(PaymentType paymentType) {
        return paymentType.equals(PaymentType.MILEAGE);
    }

    @Override
    public void payment() {
        log.info("마일리지 결제 완료!");
    }
}
