package com.decodelabs.studentmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Application Swagger/OpenAPI configuration.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Student Management REST API",
                version = "1.0.0",
                description = "API for managing student records",
                contact = @Contact(name = "DecodeLabs", email = "support@decodelabs.com"),
                license = @License(name = "MIT")
        ),
        servers = @Server(url = "/", description = "Local server")
)
public class OpenApiConfig {
}
