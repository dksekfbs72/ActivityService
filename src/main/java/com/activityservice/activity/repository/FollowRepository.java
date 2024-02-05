package com.activityservice.activity.repository;

import com.activityservice.activity.domain.entity.Follow;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByUserIdAndFollowId(Long user, Long followUser);
    @Query(value = "select f.follow_id from follow f where f.user_id = :user", nativeQuery = true)
    Optional<List<Long>> findUsersByUserId(@Param(value = "user") Long user);

    @Query(value = "select f.user_id from follow f where f.follow_id = :user", nativeQuery = true)
    Optional<List<Long>> findUserByFollowId(@Param(value = "user") Long user);
}


