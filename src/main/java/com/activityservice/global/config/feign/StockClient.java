package com.activityservice.global.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock", url = "http://localhost:8084/stock")
public interface StockClient {
    @GetMapping
    Long getStock(@RequestParam Long productId);

    @PutMapping("/addStock")
    String addStock(@RequestParam Long productId, @RequestParam Long amount);

    @PutMapping("/order")
    String order(@RequestParam Long productId);

    @PutMapping("/orderFail")
    String orderFail(@RequestParam Long productId);
}
