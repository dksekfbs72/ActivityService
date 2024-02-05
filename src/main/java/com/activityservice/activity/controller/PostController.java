package com.activityservice.activity.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.activity.domain.dto.PostForm;
import com.activityservice.activity.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public WebResponseData<String> writePost(
            @RequestHeader("Authorization") String token,
            @RequestBody PostForm postForm
    ) {
      return WebResponseData.ok(postService.writePost(token, postForm));
    }
}
