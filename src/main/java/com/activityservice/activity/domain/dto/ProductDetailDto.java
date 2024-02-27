package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductDetailDto {
    private Long id;
    private String title;
    private String description;
    private Long price;
    private Long stock;
    private LocalDateTime openAt;

    public static ProductDetailDto toDto(Product product) {
        return ProductDetailDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .title(product.getTitle())
                .openAt(product.getOpenAt())
                .build();
    }
}
