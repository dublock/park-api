package com.parkingspaces.parkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(new Info()
                        .title("REST API - Spring Park")
                        .description("API para gerenciamento de vagas de estacionamento")
                        .version("1.0")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/mit/")).contact(new Contact().name("Duanderson Block").email("contato@duanderson.block.com")));

    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um token valido")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
