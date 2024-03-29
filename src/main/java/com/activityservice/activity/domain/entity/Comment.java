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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userName;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Product product;

    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }
}
