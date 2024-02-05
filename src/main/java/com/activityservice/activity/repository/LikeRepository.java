package com.activityservice.activity.repository;

import com.activityservice.activity.domain.entity.LikeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeTable, Long> {
}
