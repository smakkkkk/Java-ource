package com.example.postnews.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Alexey Yurchenko");
        contact.setEmail("alexeyyurchenko.java@gmail.com");

        Info info = new Info()
                .title("post-news API")
                .version("1.0")
                .contact(contact)
                .description("API news portal");

        return new OpenAPI().info(info).servers(List.of(localhostServer));
    }
}