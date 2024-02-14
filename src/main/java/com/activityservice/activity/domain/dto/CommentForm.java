package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Comment;
import com.activityservice.activity.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentForm {
    private String description;

    public Comment toEntity(UserDto user, Product product) {
        return Comment.builder()
                .product(product)
                .userId(user.getId())
                .userName(user.getName())
                .text(this.description)
                .build();
    }
}
