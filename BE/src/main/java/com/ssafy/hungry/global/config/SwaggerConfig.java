package com.ssafy.hungry.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(
                title = "API",
                description = "Demo API 명세서",
                version = "1.0"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // 인증 요청 방식에서 header 추가
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        // bearerAuth 이름으로 보안 요구사항을 정의
        // 이름 설정은 자유
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                // SecurityRequirement에 정의한 bearerAuth로 위에서 정의한 securityScheme를 추가
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                // 그런 다음, 아래에 보안 규칙을 추가
                .security(Arrays.asList(securityRequirement));
    }
}