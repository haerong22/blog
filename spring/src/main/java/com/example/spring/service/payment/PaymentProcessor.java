package com.example.spring.service.payment;

public interface PaymentProcessor {

    boolean isSupported(PaymentType paymentType);

    void payment();
}
