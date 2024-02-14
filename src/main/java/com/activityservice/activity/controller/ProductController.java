package com.activityservice.activity.controller;

import com.activityservice.activity.domain.dto.ProductDetailDto;
import com.activityservice.activity.domain.dto.ProductDto;
import com.activityservice.global.dto.WebResponseData;
import com.activityservice.activity.domain.dto.ProductForm;
import com.activityservice.activity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public WebResponseData<String> writePost(
            @RequestHeader("Authorization") String token,
            @RequestBody ProductForm productForm
    ) {
      return WebResponseData.ok(productService.writePost(token, productForm));
    }

    @GetMapping
    public WebResponseData<List<ProductDto>> getProductList() {
        return WebResponseData.ok(productService.getProductList());
    }

    @GetMapping("/detail")
    public WebResponseData<ProductDetailDto> getProductDetail(@RequestParam Long productId) {
        return WebResponseData.ok(productService.getProductDetail(productId));
    }
}
