package com.activityservice.activity.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.activity.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public WebResponseData<String> follow(@RequestHeader("Authorization") String token, @RequestParam long followId) {
        return WebResponseData.ok(followService.follow(token, followId));
    }

    @GetMapping
    public List<Long> getMyFriends(@RequestParam Long userId) {
        return followService.getMyFriends(userId);
    }
}
