package com.activityservice.user.controller;

import com.activityservice.global.dto.WebResponseData;
import com.activityservice.user.domain.dto.CommentForm;
import com.activityservice.user.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public WebResponseData<String> writeComment(
            Authentication auth,
            @RequestParam long postId,
            @RequestBody CommentForm commentForm
    ) {
        return WebResponseData.ok(commentService.writeComment(auth, postId, commentForm));
    }
}
