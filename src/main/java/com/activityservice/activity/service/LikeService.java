package com.activityservice.activity.service;

import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.activity.domain.entity.Post;
import com.activityservice.activity.repository.CommentRepository;
import com.activityservice.activity.repository.LikeRepository;
import com.activityservice.activity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public String likePost(String token, long postId) {
//        User user = whoIAm(auth);
//        Post post = getThisPost(postId);
//        activityRepository.save(Activity.builder()
//                        .feedType(FeedType.LIKE)
//                        .user(user)
//                        .to(post.getUserName())
//                        .postId(post.getId())
//                .build());
//        likeRepository.save(LikeTable.builder()
//                .user(user)
//                .post(post)
//                .build());
        return "성공";
    }

    public String likeComment(String auth, long commentId) {
//        User user = whoIAm(auth);
//        Optional<Comment> optionalComment = commentRepository.findById(commentId);
//        if (optionalComment.isEmpty()) {
//            throw new ActivityException(ErrorCode.NOT_FOUND_POST);
//        }
//        Comment comment = optionalComment.get();
//        activityRepository.save(Activity.builder()
//                .feedType(FeedType.LIKE)
//                .user(user)
//                .to(comment.getUser().getName())
//                .commentId(comment.getId())
//                .build());
//        likeRepository.save(LikeTable.builder()
//                .user(user)
//                .comment(comment)
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
