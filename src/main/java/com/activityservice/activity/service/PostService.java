package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.PostForm;
import com.activityservice.activity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public String writePost(String token, PostForm postForm) {
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
