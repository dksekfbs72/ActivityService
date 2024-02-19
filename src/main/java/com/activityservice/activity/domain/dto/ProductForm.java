package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductForm {
    private String stock;
    private String title;
    private Long price;
    private String description;
    private LocalDateTime openAt;

    public Product toEntity() {
        return Product.builder()
//                .userId(user.getId())
                .price(this.price)
                .title(this.title)
                .description(this.description)
                .openAt(this.openAt)
//                .text(this.text)
//                .userName(user.getName())
                .build();
    }
}
