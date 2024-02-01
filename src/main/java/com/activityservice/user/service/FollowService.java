package com.activityservice.user.service;

import com.activityservice.user.domain.entity.Activity;
import com.activityservice.user.domain.entity.Follow;
import com.activityservice.user.domain.entity.User;
import com.activityservice.user.domain.type.FeedType;
import com.activityservice.user.repository.UserRepository;
import com.activityservice.global.exception.UserException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.user.repository.ActivityRepository;
import com.activityservice.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ActivityRepository activityRepository;

    public String follow(Authentication auth, long followId) {
        Optional<User> optionalUser = userRepository.findByEmail(auth.getName());
        Optional<User> optionalFollowUser = userRepository.findById(followId);
        if (optionalUser.isEmpty() || optionalFollowUser.isEmpty()) {
            throw new UserException(ErrorCode.NOT_FOUND_USER);
        }
        User user = optionalUser.get();
        User followUser = optionalFollowUser.get();
        if (followRepository.existsByUserAndFollowId(user, followUser)){
            throw new UserException(ErrorCode.ALREADY_FOLLOW_USER);
        }
        Follow follow = Follow.builder()
                    .user(user)
                    .followId(followUser)
                    .build();
        followRepository.save(follow);
        activityRepository.save(Activity.builder()
                        .feedType(FeedType.FOLLOW)
                        .user(user)
                        .to(followUser.getName())
                .build());
        return "팔로우 성공";
    }
}
