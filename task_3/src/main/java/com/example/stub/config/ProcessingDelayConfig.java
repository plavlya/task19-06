// src/main/java/com/example/stub/config/ProcessingDelayConfig.java
package com.example.stub.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ProcessingDelayConfig {

    @Bean
    public AtomicLong processingDelayMs(@Value("${stub.delay-ms:0}") long initialDelay) {
        return new AtomicLong(initialDelay);
    }

    @Bean
    public Gauge processingDelayGauge(MeterRegistry registry, AtomicLong processingDelayMs) {
        return Gauge.builder("stub.processing.delay_ms", processingDelayMs, AtomicLong::get)
                    .description("Текущая задержка обработки сообщений из Kafka (ms)")
                    .register(registry);
    }
}
