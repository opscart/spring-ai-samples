package com.example.ai_completion_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiCompletionApplication {

    private static final Logger log = LoggerFactory.getLogger(AiCompletionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AiCompletionApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
        return args -> {
            var chatClient = builder.build();
            log.info("Sending completion prompt to AI service. One moment please...");
            var response = chatClient.prompt()
                    .user("When was Microsoft founded?")
                    .call()
                    .content();

            log.info("Response: {}", response);
        };
    }
}
