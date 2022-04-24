package com.tools.tools.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()
                .addSecuritySchemes("basicScheme", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("basic"))).info(new Info().title("Custom API")
                .version("100")).addTagsItem(new Tag().name("mytag"));
    }
}
