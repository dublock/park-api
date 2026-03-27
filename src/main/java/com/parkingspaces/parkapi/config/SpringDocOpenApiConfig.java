package com.parkingspaces.parkapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API - Spring Park")
                        .description("API para gerenciamento de vagas de estacionamento")
                        .version("1.0")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/mit/")).contact(new Contact().name("Duanderson Block").email("contato@duanderson.block.com")));

    }
}
