package com.example.ai_chat_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AiChatApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AiChatApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AiChatApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
		return args -> {
			var chatClient = builder.build();
			log.info("Sending chat prompts to AI service. One moment please...");
			String response = chatClient.prompt()
					.system("You are a helpful assistant answering questions about Azure AI Services.")
					.user("Does Azure OpenAI support customer managed keys? Do other Azure AI service support this too?")
					.call()
					.content();

			log.info("Response: {}", response);
		};
	}
}
