// src/main/java/com/example/stub/service/KafkaMessageListener.java
package com.example.stub.service;

import com.example.stub.dto.MessageDto;
import com.example.stub.model.RecordEntity;
import com.example.stub.repository.RecordRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class KafkaMessageListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    private final RecordRepository repository;
    private final AtomicLong delayMs;  // вместо long

    public KafkaMessageListener(RecordRepository repository,
                                AtomicLong processingDelayMs) {
        this.repository = repository;
        this.delayMs    = processingDelayMs;
    }

    @KafkaListener(topics = "${stub.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(MessageDto msg) {
        // 1) лог о вычитке
        log.info("[Read from Kafka] {}", msg);

        // 2) фиксируем время до задержки или после? Можно до и записать чтение, а пишем после задержки.
        long timeRq = Instant.now().getEpochSecond();

        // 3) искусственная задержка
        long delay = delayMs.get();
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted during delay", e);
            }
        }

        // 4) сохраняем в БД
        RecordEntity record = new RecordEntity(msg.getMsgUuid(), msg.isHead(), timeRq);
        repository.save(record);

        // 5) лог о записи в БД, с указанием задержки в логе при желании
        log.info("[Write to DB after {}ms] {{\"msgUuid\":\"{}\",\"head\":{},\"timeRq\":\"{}\"}}",
                 delay,
                 record.getMsgUuid(),
                 record.isHead(),
                 record.getTimeRq()
        );
    }
}
