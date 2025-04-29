package com.example.ai_custom_data_demo;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Context;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.azure.AzureVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class AiCustomDataApplication {

	private static final Logger log = LoggerFactory.getLogger(AiCustomDataApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AiCustomDataApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
	    return args -> {
			var chatClient = builder
					.defaultSystem("""
								You are a helpful assistant. Use the information from the DOCUMENTS section to augment answers.
								DOCUMENTS:
								{documents}
							""")
					.build();

			var response = chatClient.prompt()
					.system("")
					.user("")
					.call()
					.content();
	    };
	}

	// this can go away in favor of a setting
	// spring.ai.vectorstore.azure.index-name
	@Bean
	public SearchIndexClient searchIndexClient() {
		return new SearchIndexClientBuilder().endpoint(System.getenv("AZURE_AI_SEARCH_ENDPOINT"))
				.credential(new AzureKeyCredential(System.getenv("AZURE_AI_SEARCH_API_KEY")))
				.buildClient();
	}

	// pdf reader load pdf


	// ingestion phase - load the pdf
	// we want to only load this 1x
	@Bean
	public VectorStore vectorStore(SearchIndexClient searchIndexClient, EmbeddingModel embeddingModel) {
		return new AzureVectorStore(searchIndexClient, embeddingModel, true);
	}

}
