package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.ActivityForm;
import com.activityservice.activity.domain.dto.PostForm;
import com.activityservice.activity.domain.dto.UserDto;
import com.activityservice.activity.repository.PostRepository;
import com.activityservice.global.type.FeedType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    public String writePost(String token, PostForm postForm) {
        UserDto user = getUser(token);
        long postId = postRepository.save(postForm.toEntity(user)).getId();
        toBeActivity(ActivityForm.builder()
                .userId(user.getId())
                .feedType(FeedType.POST)
                .title(postForm.getTitle())
                .userName(user.getName())
                .postId(postId)
                .build());
        return "성공";
    }

    private UserDto getUser(String token) {
        String url = "http://localhost:8080/user/info";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto;
        try {
            userDto = objectMapper.readValue(response.getBody(), UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }

    private void toBeActivity(ActivityForm activityForm) {
        String url = "http://localhost:8082/feed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ActivityForm> request = new HttpEntity<>(activityForm, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
