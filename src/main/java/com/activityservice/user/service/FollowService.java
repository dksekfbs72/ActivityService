package com.activityservice.user.service;

import com.activityservice.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public String follow(Authentication auth, long followId) {
//        Optional<User> optionalUser = userRepository.findByEmail(auth.getName());
//        Optional<User> optionalFollowUser = userRepository.findById(followId);
//        if (optionalUser.isEmpty() || optionalFollowUser.isEmpty()) {
//            throw new ActivityException(ErrorCode.NOT_FOUND_USER);
//        }
//        User user = optionalUser.get();
//        User followUser = optionalFollowUser.get();
//        if (followRepository.existsByUserAndFollowId(user, followUser)){
//            throw new ActivityException(ErrorCode.ALREADY_FOLLOW_USER);
//        }
//        Follow follow = Follow.builder()
//                    .user(user)
//                    .followId(followUser)
//                    .build();
//        followRepository.save(follow);
//        activityRepository.save(Activity.builder()
//                        .feedType(FeedType.FOLLOW)
//                        .user(user)
//                        .to(followUser.getName())
//                .build());
        return "팔로우 성공";
    }
}
