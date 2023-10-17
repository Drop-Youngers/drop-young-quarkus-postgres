package com.dropyoung.quarkus;


import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "Auth", description = "Authentication operations."),
                @Tag(name = "Users", description = "User operations."),
                @Tag(name = "Files", description = "File operations.")
        },
        info = @Info(
                title = "Quarkus Starter API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Mugisha Precieux",
                        url = "https://mugishap.pro",
                        email = "precieuxmugisha@gmail.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                description = "API Endpoint for Quarkus Starter",
                termsOfService = "https://mugishap.pro/terms"
        )
)
public class StarterApplication extends Application {
}
