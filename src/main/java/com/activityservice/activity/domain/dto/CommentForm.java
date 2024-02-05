package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Comment;
import com.activityservice.activity.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentForm {
    private String description;

    public Comment toEntity(Long user, Post post) {
        return Comment.builder()
//                .post(post)
//                .user(user)
//                .text(this.description)
                .build();
    }
}
