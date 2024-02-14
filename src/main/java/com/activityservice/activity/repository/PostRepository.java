package com.activityservice.activity.repository;

import com.activityservice.activity.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Product, Long> {
}
