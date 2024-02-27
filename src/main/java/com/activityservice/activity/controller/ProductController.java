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
@RequestMapping("/activity/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public WebResponseData<String> addProduct(
            @RequestBody ProductForm productForm
    ) {
      return WebResponseData.ok(productService.addProduct(productForm));
    }

    @GetMapping
    public WebResponseData<List<ProductDto>> getProductList() {
        return WebResponseData.ok(productService.getProductList());
    }

    @GetMapping("/stock")
    public Long getStock(@RequestParam Long productId) {
        return productService.getStock(productId);
    }

    @GetMapping("/detail")
    public WebResponseData<ProductDetailDto> getProductDetail(@RequestParam Long productId) {
        return WebResponseData.ok(productService.getProductDetail(productId));
    }
}
