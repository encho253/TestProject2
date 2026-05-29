package com.example.interview.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library Interview API",
                version = "1.0",
                description = "Junior Java Spring Boot interview exercise"
        )
)
public class OpenApiConfig {
}
