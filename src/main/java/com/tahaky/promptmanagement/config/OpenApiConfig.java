package com.tahaky.promptmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Prompt Management Service API")
                        .description("API for managing AI system prompts with versioning support")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("tahaky")
                                .url("https://github.com/tahaky/prompt-managemetn-service")));
    }
}
