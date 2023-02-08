package com.example.spring.service.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentProcessTest {

    @Autowired
    PaymentProcess paymentProcess;

    @Test
    void payment() {
        paymentProcess.payment(PaymentType.WON);
        paymentProcess.payment(PaymentType.MILEAGE);
        paymentProcess.payment(PaymentType.DOLLAR);
    }
}