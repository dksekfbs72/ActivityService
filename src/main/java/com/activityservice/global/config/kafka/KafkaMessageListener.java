package com.activityservice.global.config.kafka;

import com.activityservice.activity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class KafkaMessageListener {
    private static final long PROCESS_INTERVAL = 3000; // 일정 시간 (밀리초 단위)
    private final LinkedBlockingQueue<Long> pendingIds = new LinkedBlockingQueue<>(); // Kafka 이벤트가 도착할 때마다 ID를 여기에 추가합니다.
    private final Lock lock = new ReentrantLock();

    @Autowired
    private OrderService orderService; // 실제로 일괄 처리할 서비스

    @KafkaListener(topics = "order-topic", groupId = "order-service")
    public void handleOrderEvent(String productId) {
        pendingIds.add(Long.parseLong(productId));
    }

    @Scheduled(fixedRate = PROCESS_INTERVAL)
    public void processCollectedIds() {
        if (lock.tryLock()) {
            try {
                List<Long> collectedIds = new ArrayList<>();
                if (!pendingIds.isEmpty()) {
                    pendingIds.drainTo(collectedIds);

                    orderService.saveOrder(collectedIds);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
