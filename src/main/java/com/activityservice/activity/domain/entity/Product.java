package com.activityservice.activity.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "user_asc", columnList = "user_id")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long stock;
    private Long price;
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime openAt;
}
