package com.example.aichatdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    // Request DTO
    public static class ChatRequest {
        public String userMessage;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        var chatClient = chatClientBuilder.build();
        return chatClient.prompt()
                         .user(request.userMessage)
                         .call()
                         .content();
    }
}
