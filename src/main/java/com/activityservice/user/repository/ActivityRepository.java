package com.activityservice.user.repository;

import com.activityservice.user.domain.entity.Activity;
import com.activityservice.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByUserInOrderByCreateAtDesc(List<User> friends, PageRequest of);
}
