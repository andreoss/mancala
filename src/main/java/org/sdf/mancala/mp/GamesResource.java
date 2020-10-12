package org.sdf.mancala.mp;

import java.net.URI;
import java.util.Collections;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private static final JsonBuilderFactory JSON =
        Json.createBuilderFactory(Collections.emptyMap());

    /**
     * The games.
     */
    private final Games games;

    /**
     * Ctor & injection point.
     *
     * @param games The games
     */
    @Inject
    public GamesResource(final Games games) {
        this.games = games;
    }

    /**
     * Return a game by id.
     *
     * @return {@link JsonObject}
     */
    @Override @SuppressWarnings("checkstyle:designforextension")
    @GET
    @Path("/{gid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("gid") final int id) {
        if (!this.games.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(this.gameAsJson(this.games.find(id))).build();
    }

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
