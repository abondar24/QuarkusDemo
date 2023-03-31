package org.abondar.experimental.quarkusdemo.rest;

import jakarta.ws.rs.core.Application;import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;


@OpenAPIDefinition(
        info = @Info(
                title = "Person API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Person Api",
                        email = "abondar1992@gmail.com"),
                license = @License(name = "MIT"))
)
public class ApiApp extends Application {
}
