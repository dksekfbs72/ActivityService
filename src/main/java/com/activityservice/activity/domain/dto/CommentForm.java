package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Comment;
import com.activityservice.activity.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentForm {
    private String description;

    public Comment toEntity(UserDto user, Post post) {
        return Comment.builder()
                .post(post)
                .userId(user.getId())
                .userName(user.getName())
                .text(this.description)
                .build();
    }
}
