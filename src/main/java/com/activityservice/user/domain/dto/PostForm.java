package com.activityservice.user.domain.dto;

import com.activityservice.user.domain.entity.Post;
import com.activityservice.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostForm {
    private String title;
    private String text;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(this.title)
                .text(this.text)
                .userName(user.getName())
                .build();
    }
}
