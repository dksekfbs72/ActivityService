package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.ProductDetailDto;
import com.activityservice.activity.domain.dto.ProductDto;
import com.activityservice.activity.domain.dto.ProductForm;
import com.activityservice.activity.domain.entity.Product;
import com.activityservice.activity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public String addProduct(ProductForm productForm) {
        long productId = productRepository.save(productForm.toEntity()).getId();
        redisTemplate.opsForValue().set(String.valueOf(productId), productForm.getStock());

        /* 뉴스피드 사용 시 필요
        UserDto user = getUser(token);
        toBeActivity(ActivityForm.builder()
                .userId(user.getId())
                .feedType(FeedType.POST)
                .title(productForm.getTitle())
                .userName(user.getName())
                .postId(postId)
                .build());
         */

        return "성공";
    }

    /* 뉴스피드 사용 시 필요
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
     */

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
        Object stock = redisTemplate.opsForValue().get(String.valueOf(productId));
        Long sumStockAndAmount = Long.parseLong(stock == null ? "0" : (String) stock) + amount;
        redisTemplate.opsForValue().set(String.valueOf(productId), String.valueOf(sumStockAndAmount));
        return "상품 수량 추가 성공";
    }

    public Long getStock(Long productId) {
        Object stock = redisTemplate.opsForValue().get(String.valueOf(productId));
        return Long.valueOf(stock == null ? "-1" : (String) stock);
    }
}
