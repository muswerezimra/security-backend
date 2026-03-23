package com.zimra.engine.user.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth", // Name of the security scheme
        type = SecuritySchemeType.HTTP, // Type of the security scheme
        scheme = "bearer", // Scheme (e.g., bearer, basic)
        bearerFormat = "JWT" // Format of the token (e.g., JWT)
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API")
                        .version("1.0.0")
                        .description("API documentation for Spring Boot application"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")); // Add global security requirement
    }
}