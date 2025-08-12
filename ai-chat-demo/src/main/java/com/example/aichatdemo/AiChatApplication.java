package com.example.aichatdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiChatApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AiChatApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AiChatApplication.class, args);
	}
	@Bean
	CommandLineRunner printApiKey(@Value("${spring.ai.openai.api-key:NOT_SET}") String apiKey) {
		return args -> {
			log.info("spring.ai.openai.api-key = {}", apiKey);
		};
	}

	@Bean
	CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
		return args -> {
			var chatClient = builder.build();
			log.info("Sending chat prompts to AI service. One moment please...");
			String response = chatClient.prompt()
					.user("What was Microsoft's original internal codename for the project that eventually became Azure?")
					.call()
					.content();

			log.info("Response: {}", response);
		};
	}
}
