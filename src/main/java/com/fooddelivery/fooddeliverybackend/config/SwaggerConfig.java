package com.fooddelivery.fooddeliverybackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // API INFO
                .info(
                        new Info()
                                .title("Smart Food Delivery API")
                                .version("1.0")
                                .description("JWT Secured Food Delivery Backend")
                )

                // JWT SECURITY
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearerAuth")
                )

                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes(
                                        "bearerAuth",

                                        new SecurityScheme()
                                                .name("bearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}