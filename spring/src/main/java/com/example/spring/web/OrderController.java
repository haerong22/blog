package com.example.spring.web;

import com.example.spring.service.OrderService;
import com.example.spring.web.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String order(@RequestBody OrderRequest orderRequest) {
        orderService.order(orderRequest);
        return "success";
    }
}
