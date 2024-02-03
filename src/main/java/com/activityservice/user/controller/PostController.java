package com.activityservice.user.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.user.domain.dto.PostForm;
import com.activityservice.user.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public WebResponseData<String> writePost(
            Authentication auth,
            @RequestBody PostForm postForm
    ) {
      return WebResponseData.ok(postService.writePost(auth, postForm));
    }
}
