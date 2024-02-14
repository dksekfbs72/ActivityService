package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductForm {
    private Long stock;
    private String title;
    private Long price;

    public Product toEntity(UserDto user) {
        return Product.builder()
//                .userId(user.getId())
                .stock(this.stock)
                .price(this.price)
                .title(this.title)
//                .text(this.text)
//                .userName(user.getName())
                .build();
    }
}
