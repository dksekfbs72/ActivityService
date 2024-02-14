package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ProductDto {
    private Long id;
    private String title;
    private LocalDateTime openAt;

    private static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .openAt(product.getOpenAt())
                .build();
    }

    public static List<ProductDto> toList(List<Product> productList) {
        return productList.stream()
                .map(ProductDto::toDto)
                .toList();
    }
}
