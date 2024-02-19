package com.activityservice.activity.domain.entity;

import com.activityservice.activity.domain.type.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long relUserId;
    private Long relProduct;
    private OrderStatus status;
}
