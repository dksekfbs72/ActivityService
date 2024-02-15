package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.*;
import com.activityservice.activity.domain.entity.Product;
import com.activityservice.activity.repository.ProductRepository;
import com.activityservice.global.type.FeedType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final RedisTemplate<Long, Long> redisTemplate;

    public String writePost(String token, ProductForm productForm) {
        UserDto user = getUser(token);
        long postId = productRepository.save(productForm.toEntity(user)).getId();
        toBeActivity(ActivityForm.builder()
                .userId(user.getId())
                .feedType(FeedType.POST)
                .title(productForm.getTitle())
                .userName(user.getName())
                .postId(postId)
                .build());
        return "성공";
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

    public List<ProductDto> getProductList() {
        return ProductDto.toList(productRepository.findAll());
    }

    public ProductDetailDto getProductDetail(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException();
        }

        return ProductDetailDto.toDto(optionalProduct.get());
    }

    public String addStock(Long productId, Long amount) {
        Long stock = redisTemplate.opsForValue().get(productId);
        redisTemplate.opsForValue().set(productId, (stock == null ? 0 : stock) + amount);
        return "상품 수량 추가 성공";
    }

    public Long getStock(Long productId) {
        return redisTemplate.opsForValue().get(productId);
    }
}
