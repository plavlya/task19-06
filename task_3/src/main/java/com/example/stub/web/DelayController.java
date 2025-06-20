// src/main/java/com/example/stub/web/DelayController.java
package com.example.stub.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/delay")
public class DelayController {
    private final AtomicLong processingDelayMs;

    public DelayController(AtomicLong processingDelayMs) {
        this.processingDelayMs = processingDelayMs;
    }

    @GetMapping
    public long getDelay() {
        return processingDelayMs.get();
    }

    @PostMapping
    public ResponseEntity<String> setDelay(@RequestParam("ms") long newDelay) {
        if (newDelay < 0) {
            return ResponseEntity.badRequest().body("Delay must be >= 0");
        }
        processingDelayMs.set(newDelay);
        return ResponseEntity.ok("Processing delay set to " + newDelay + " ms");
    }
}
