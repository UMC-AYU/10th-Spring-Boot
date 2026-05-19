package com.example.springboot.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("UMC10th").description("10기 Swagger").version("0.0.1");

        // JWT 토큰 헤더 방식
        String jwtScheme = "JWT TOKEN";
        // Basic Auth (Swagger 로그인 테스트용)
        String basicScheme = "Basic Auth";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(jwtScheme)
                .addList(basicScheme);

        Components components = new Components()
                .addSecuritySchemes(jwtScheme, new SecurityScheme()
                        .name(jwtScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(basicScheme, new SecurityScheme()
                        .name(basicScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic"));  // Swagger 로그인 테스트를 위한 추가

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
