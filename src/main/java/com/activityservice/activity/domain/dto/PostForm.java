package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostForm {
    private String title;
    private String text;

    public Post toEntity(UserDto user) {
        return Post.builder()
                .userId(user.getId())
                .title(this.title)
                .text(this.text)
                .userName(user.getName())
                .build();
    }
}
