package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.ActivityForm;
import com.activityservice.activity.domain.dto.UserDto;
import com.activityservice.activity.domain.entity.Comment;
import com.activityservice.activity.domain.entity.LikeTable;
import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.type.ErrorCode;
import com.activityservice.activity.domain.entity.Product;
import com.activityservice.activity.repository.CommentRepository;
import com.activityservice.activity.repository.LikeRepository;
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
public class LikeService {
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public String likePost(String token, long postId) {
        UserDto user = getUser(token);
        Product product = getThisPost(postId);
        likeRepository.save(LikeTable.builder()
                .userId(user.getId())
                .product(product)
                .build());
        toBeActivity(ActivityForm.builder()
                .feedType(FeedType.LIKE)
                .userName(user.getName())
                .userId(user.getId())
//                .to(product.getUserName())
                .postId(postId)
                .build());
        return "성공";
    }

    public String likeComment(String token, long commentId) {
        UserDto user = getUser(token);
        Comment comment = getThisComment(commentId);
        likeRepository.save(LikeTable.builder()
                .userId(user.getId())
                .comment(comment)
                .build());
        toBeActivity(ActivityForm.builder()
                .feedType(FeedType.LIKE)
                .userName(user.getName())
                .userId(user.getId())
                .to(comment.getUserName())
                .commentId(commentId)
                .build());
        return "성공";
    }

    public Product getThisPost(long postId) {
        Optional<Product> optionalPost = productRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ActivityException(ErrorCode.NOT_FOUND_POST);
        }
        return optionalPost.get();
    }

    public Comment getThisComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new ActivityException(ErrorCode.NOT_FOUND_COMMENT);
        }
        return optionalComment.get();
    }

    private void toBeActivity(ActivityForm activityForm) {
        String url = "http://newsFeedService:8082/feed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ActivityForm> request = new HttpEntity<>(activityForm, headers);

        restTemplate.postForEntity(url, request, String.class);
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
}
