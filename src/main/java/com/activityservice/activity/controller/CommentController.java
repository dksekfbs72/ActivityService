package com.activityservice.activity.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.activity.domain.dto.CommentForm;
import com.activityservice.activity.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public WebResponseData<String> writeComment(
            @RequestHeader("Authorization") String token,
            @RequestParam long postId,
            @RequestBody CommentForm commentForm
    ) {
        return WebResponseData.ok(commentService.writeComment(token, postId, commentForm));
    }
}
