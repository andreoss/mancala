package org.sdf.mancala.mp;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * Activate JAX-RS.
 * <p>
 * All REST Endpoints available under /games
 */
@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(
        title = "The Game of Mancala",
        version = "1.0.0"
    )
)
public final class Mancala extends Application {
}
