package com.example.spring.web.request;

import com.example.spring.service.payment.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private PaymentType paymentType;
}
