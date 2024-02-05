package com.activityservice.global.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedType {
    POST,
    COMMENT,
    FOLLOW,
    LIKE
}
