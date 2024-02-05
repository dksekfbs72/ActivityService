package com.activityservice.activity.service;

import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.activity.domain.dto.CommentForm;
import com.activityservice.activity.domain.entity.Post;
import com.activityservice.activity.repository.CommentRepository;
import com.activityservice.activity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public String writeComment(String token, long postId, CommentForm commentForm) {
//        User user = whoIAm(auth);
//        Post post = getThisPost(postId);
//        activityRepository.save(Activity.builder()
//                        .feedType(FeedType.COMMENT)
//                        .user(user)
//                        .to(post.getUserName())
//                        .postId(post.getId())
//                        .commentId(commentRepository.save(commentForm.toEntity(user, post)).getId())
//                .build());
        return "성공";
    }

    public Post getThisPost(long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ActivityException(ErrorCode.NOT_FOUND_POST);
        }
        return optionalPost.get();
    }
}
