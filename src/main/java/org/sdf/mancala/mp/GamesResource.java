package org.sdf.mancala.mp;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.cactoos.map.MapOf;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.sdf.mancala.Game;
import org.sdf.mancala.Games;

/**
 * Endpoint.
 */
@RequestScoped
@Path("/games")
@Tag(name = "Game endpoint")
public class GamesResource implements GamesApi {
    /**
     * Json factory.
     */
    private static final JsonBuilderFactory JSON =
        Json.createBuilderFactory(new MapOf<>());

    /**
     * The games.
     */
    private final Games games;

    /**
     * Ctor & injection point.
     *
     * @param gms The games
     */
    @Inject
    public GamesResource(final Games gms) {
        this.games = gms;
    }

    /**
     * Return a game by id.
     *
     * @return {@link JsonObject}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    @GET
    @Path("/{gid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("gid") final int id) {
        if (!this.games.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(this.gameAsJson(this.games.find(id))).build();
    }

    /**
     * Write game as json.
     * @param game A game
     * @return json
     */
    private JsonObject gameAsJson(final Game game) {
        final var status = JSON.createObjectBuilder();
        for (int i = 0; i < game.board().size(); i++) {
            status.add(String.valueOf(i + 1), game.board().pit(i).count());
        }
        return JSON
            .createObjectBuilder()
            .add("id", game.id())
            .add("status", status)
            .build();
    }

    /**
     * Return a greeting message using the name that was provided.
     *
     * @param gid the id of game
     * @param pid the id of pit
     * @return {@link Response}
     */
    @Override @SuppressWarnings("checkstyle:designforextension")
    @Path("/{gameId}/pits/{pitId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(
        @PathParam("gameId") final int gid,
        @PathParam("pitId") final int pid
    ) {
        final var game = this.games.find(gid);
        game.play(game.players().current(), pid - 1);
        return Response
            .accepted()
            .location(URI.create(String.format("/%d", game.id())))
            .build();
    }


    /**
     * Start new game.
     *
     * @return {@link Response}
     */
    @Override @SuppressWarnings("checkstyle:designforextension")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        name = "normal",
        responseCode = "201",
        description = "Game created"
    )
    public Response startGame() {
        final Game game = this.games.start();
        return Response.created(
            URI.create(String.format("/%d", game.id()))
        ).build();
    }

}
