package com.ssm.git_client.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/git-client", description = "Default Server URL")})
public class SwaggerConfig {

    private static final String GIT_CLIENT_TOOL = "Git Client tool";

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder().group(GIT_CLIENT_TOOL).pathsToMatch("/**").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "basicScheme",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")))
                .info(getApiInfo());
    }

    private Info getApiInfo() {
        return new Info()
                .title(GIT_CLIENT_TOOL)
                .version("1.0.0")
                .description(String.format("%s %s", GIT_CLIENT_TOOL, "Api Documentation"))
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }
}
