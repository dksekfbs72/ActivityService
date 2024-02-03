package com.activityservice.user.service;

import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.user.domain.dto.CommentForm;
import com.activityservice.user.domain.entity.Post;
import com.activityservice.user.repository.CommentRepository;
import com.activityservice.user.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public String writeComment(Authentication auth, long postId, CommentForm commentForm) {
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
