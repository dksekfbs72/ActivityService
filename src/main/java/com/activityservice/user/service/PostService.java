package com.activityservice.user.service;

import com.activityservice.user.domain.dto.PostForm;
import com.activityservice.user.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public String writePost(Authentication auth, PostForm postForm) {
//        User user = whoIAm(auth);
//        long postId = postRepository.save(postForm.toEntity(user)).getId();
//        activityRepository.save(Activity.builder()
//                        .feedType(FeedType.POST)
//                        .title(postForm.getTitle())
//                        .user(user)
//                        .postId(postId)
//                .build());
        return "성공";
    }
}
