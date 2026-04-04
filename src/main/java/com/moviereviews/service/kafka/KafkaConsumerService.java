/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "my_topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Message received: " + message);
    }
}
