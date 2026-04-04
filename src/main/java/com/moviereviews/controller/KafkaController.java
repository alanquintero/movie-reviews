/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviereviews.service.kafka.KafkaProducerService;

@RestController
public class KafkaController {

    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage(message);
        return "Message sent successfully!";
    }
}
