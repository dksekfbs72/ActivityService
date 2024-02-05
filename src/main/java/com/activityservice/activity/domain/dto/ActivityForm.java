package com.activityservice.activity.domain.dto;

import com.activityservice.global.type.FeedType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ActivityForm {
    private Long userId;
    private FeedType feedType;
    private String title;
    private String userName;
    private String to;
    private Long postId;
    private Long commentId;
}
