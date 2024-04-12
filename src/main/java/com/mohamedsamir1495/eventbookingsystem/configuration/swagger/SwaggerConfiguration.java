package com.mohamedsamir1495.eventbookingsystem.configuration.swagger;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Event Booking REST API Documentation",
                description = "Event Booking microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Mohamed Samir",
                        email = "mohamedsamir1495@gmail.com",
                        url = "https://www.linkedin.com/in/mohamedsamir1495"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/mohamedsamir1495"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "Event Booking microservice REST API Documentation",
                url = "https://github.com/mohamedsamir1495"
        ),
        security = { @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Bearer") })
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "Authorization")
public class SwaggerConfiguration {

}
