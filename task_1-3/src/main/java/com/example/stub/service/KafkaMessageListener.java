package com.example.stub.service;

import com.example.stub.dto.MessageDto;
import com.example.stub.model.RecordEntity;
import com.example.stub.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Component
public class KafkaMessageListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    private final RecordRepository repository;
    private final long delayMs;

    public KafkaMessageListener(RecordRepository repository,
                                @Value("${stub.delay-ms:0}") long delayMs) {
        this.repository = repository;
        this.delayMs    = delayMs;
    }

    @KafkaListener(topics = "${stub.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(MessageDto msg) {
        // 1) лог о вычитке
        log.info("[Read from Kafka] {}", msg.toString());

        // 2) определяем время
        long timeRq = Instant.now().getEpochSecond();

        // 3) искусственная задержка (Задание 3)
        if (delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted during delay", e);
            }
        }

        // 4) сохраняем в БД
        RecordEntity record = new RecordEntity(msg.getMsgUuid(), msg.isHead(), timeRq);
        repository.save(record);

        // 5) лог о записи в БД
        log.info("[Write to DB] {{\"msgUuid\":\"{}\",\"head\":{},\"timeRq\":\"{}\"}}",
                 record.getMsgUuid(),
                 record.isHead(),
                 record.getTimeRq()
        );
    }
}
