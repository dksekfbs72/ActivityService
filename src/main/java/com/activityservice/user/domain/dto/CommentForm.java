package com.activityservice.user.domain.dto;

import com.activityservice.user.domain.entity.Comment;
import com.activityservice.user.domain.entity.Post;
import com.activityservice.user.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentForm {
    private String description;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .post(post)
                .user(user)
                .text(this.description)
                .build();
    }
}
