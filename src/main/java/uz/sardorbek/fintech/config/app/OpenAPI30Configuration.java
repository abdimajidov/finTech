package uz.sardorbek.fintech.config.app;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPI30Configuration {
    private static final String SECURITY_SCHEME_NAME_BASIC = "basicAuth";
    private static final String SECURITY_SCHEME_NAME_BEARER = "bearerAuth";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME_BEARER, createBearerSecurityScheme()))
                .addSecurityItem(createBearerSecurityRequirement());
    }


    private SecurityScheme createBearerSecurityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME_BEARER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }

    private SecurityRequirement createBearerSecurityRequirement() {
        SecurityRequirement requirement = new SecurityRequirement();
        requirement.addList(SECURITY_SCHEME_NAME_BEARER);
        return requirement;
    }
}
