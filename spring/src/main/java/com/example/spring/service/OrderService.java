package com.example.spring.service;

import com.example.spring.service.payment.PaymentProcess;
import com.example.spring.web.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PaymentProcess paymentProcess;

    public void order(OrderRequest orderRequest) {
        paymentProcess.payment(orderRequest.getPaymentType());
    }
}
