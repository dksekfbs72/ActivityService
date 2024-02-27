package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.ActivityForm;
import com.activityservice.activity.domain.dto.UserDto;
import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.activity.domain.dto.CommentForm;
import com.activityservice.activity.domain.entity.Product;
import com.activityservice.activity.repository.CommentRepository;
import com.activityservice.activity.repository.ProductRepository;
import com.activityservice.global.type.FeedType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final RestTemplate restTemplate;
    public String writeComment(String token, long postId, CommentForm commentForm) {
        UserDto user = getUser(token);
        Product product = getThisPost(postId);
        toBeActivity(ActivityForm.builder()
                .feedType(FeedType.COMMENT)
                .userId(user.getId())
                .userName(user.getName())
//                .to(product.getUserName())
                .postId(postId)
                .commentId(commentRepository.save(commentForm.toEntity(user, product)).getId())
                .build());
        return "성공";
    }

    public Product getThisPost(long postId) {
        Optional<Product> optionalPost = productRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ActivityException(ErrorCode.NOT_FOUND_PRODUCT);
        }
        return optionalPost.get();
    }

    private UserDto getUser(String token) {
        String url = "http://userService:8080/user/info";
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
        String url = "http://newsFeedService:8082/feed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ActivityForm> request = new HttpEntity<>(activityForm, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
