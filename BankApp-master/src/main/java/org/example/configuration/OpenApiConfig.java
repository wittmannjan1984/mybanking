package org.example.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank application API",
                description = "Bank application", version = "1.1",
                contact = @Contact(
                        name = "Jan Wittmann",
                        email = "wittmannjan1984@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "basicauth",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)

public class OpenApiConfig {
//
}
