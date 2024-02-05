package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.ActivityForm;
import com.activityservice.activity.domain.dto.UserFollowDto;
import com.activityservice.activity.domain.entity.Follow;
import com.activityservice.activity.repository.FollowRepository;
import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.global.type.FeedType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final RestTemplate restTemplate;

    public String follow(String token, long followId) {
        UserFollowDto user = getUserInfoForFollow(token, followId);

        if (followRepository.existsByUserIdAndFollowId(user.getId(), followId)){
            throw new ActivityException(ErrorCode.ALREADY_FOLLOW_USER);
        }
        Follow follow = Follow.builder()
                    .userId(user.getId())
                    .followId(followId)
                    .build();
        followRepository.save(follow);
        toBeActivity(ActivityForm.builder()
                .userId(user.getId())
                .feedType(FeedType.FOLLOW)
                .userName(user.getName())
                .to(user.getFollowUserName())
                .build());
        return "팔로우 성공";
    }

    public List<Long> getMyFriends(Long userId) {
        List<Long> friends = new ArrayList<>();
        friends.add(userId);
        Optional<List<Long>> following = followRepository.findUsersByUserId(userId);
        Optional<List<Long>> follower = followRepository.findUserByFollowId(userId);
        following.ifPresent(longs -> friends.addAll(following.get()));
        follower.ifPresent(longs -> friends.addAll(follower.get()));

        return friends;
    }

    private UserFollowDto getUserInfoForFollow(String token, Long followId) {
        String url = "http://localhost:8080/user/infoForFollow?followId="+ followId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        UserFollowDto userFollowDto;
        try {
            userFollowDto = objectMapper.readValue(response.getBody(), UserFollowDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userFollowDto;
    }

    private void toBeActivity(ActivityForm activityForm) {
        String url = "http://localhost:8082/feed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ActivityForm> request = new HttpEntity<>(activityForm, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
