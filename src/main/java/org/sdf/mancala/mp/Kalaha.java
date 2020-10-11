package org.sdf.mancala.mp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * Activate JAX-RS.
 * <p>
 * All REST Endpoints available under /games
 */
@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(
        title = "The Kalah Game",
        version = "1.0.0",
        contact = @Contact(
            name = "Phillip Kruger",
            email = "phillip.kruger@phillip-kruger.com",
            url = "http://www.phillip-kruger.com"
        )
    )
)
public final class Kalaha extends Application {
}