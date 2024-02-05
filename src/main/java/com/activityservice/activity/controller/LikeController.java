package com.activityservice.activity.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.activity.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likePost")
    public WebResponseData<String> likePost(
            @RequestHeader("Authorization") String token,
            @RequestParam long postId
    ) {
        return WebResponseData.ok(likeService.likePost(token, postId));
    }
    @PostMapping("/likeComment")
    public WebResponseData<String> likeComment(
            @RequestHeader("Authorization") String token,
            @RequestParam long commentId
    ) {
        return WebResponseData.ok(likeService.likeComment(token, commentId));
    }
}