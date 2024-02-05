package com.activityservice.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserFollowDto {
    private Long id;
    private String name;
    private String email;
    private String followUserName;
    @JsonCreator
    public UserFollowDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("followUserName") String followUserName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.followUserName = followUserName;
    }
}
