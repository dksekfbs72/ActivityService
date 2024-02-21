package com.activityservice.activity.controller;

import com.activityservice.activity.domain.dto.OrderStatusDto;
import com.activityservice.activity.service.OrderService;
import com.activityservice.global.dto.WebResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public WebResponseData<OrderStatusDto> order(@RequestParam Long productId) {
        return WebResponseData.ok(orderService.order(productId));
    }

    @PutMapping
    public WebResponseData<OrderStatusDto> paymentTry(@RequestParam Long orderId) {
        return WebResponseData.ok(orderService.paymentTry(orderId));
    }
}
