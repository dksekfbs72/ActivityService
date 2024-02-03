package com.activityservice.user.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.user.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likePost")
    public WebResponseData<String> likePost(
            Authentication auth,
            @RequestParam long postId
    ) {
        return WebResponseData.ok(likeService.likePost(auth, postId));
    }
    @PostMapping("/likeComment")
    public WebResponseData<String> likeComment(
            Authentication auth,
            @RequestParam long commentId
    ) {
        return WebResponseData.ok(likeService.likeComment(auth, commentId));
    }
}
