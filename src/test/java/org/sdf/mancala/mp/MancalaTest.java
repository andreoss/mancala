package org.sdf.mancala.mp;

import io.helidon.microprofile.server.Server;
import java.net.HttpURLConnection;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class MancalaTest {

    private String url;

    private Client client;

    @BeforeEach
    void startTheServer() throws Exception {
        final Server server = Server.create().start();
        this.url = "http://localhost:" + server.port();
        this.client = ClientBuilder.newClient();
    }

    @Test
    void gameIsCreated() {
        final Response response = this.client
            .target(this.url)
            .path("games")
            .request()
            .method("POST");
        MatcherAssert.assertThat(
            "must be created with status 201",
            response.getStatus(),
            Matchers.is(HttpURLConnection.HTTP_CREATED)
        );
    }


    @Test
    void gameIsCreatedWithCorrectHeaders() {
        final Response response = this.client
            .target(this.url)
            .path("games")
            .request()
            .method("POST");
        MatcherAssert.assertThat(
            "must have Location header",
            response.getHeaderString("Location"),
            Matchers.endsWith("/1")
        );
    }

    @Test
    void whenNoSuchGame() {
        final Response response = this.client
            .target(this.url)
            .path("games/12345")
            .request()
            .method("GET");
        MatcherAssert.assertThat(
            response.getStatus(),
            Matchers.is(HttpURLConnection.HTTP_NOT_FOUND)
        );
    }

    @Test
    void gameById() {
        final Response create = this.client
            .target(this.url)
            .path("games")
            .request()
            .method("POST");
        final Response response = this.client
            .target(this.url)
            .path("games/1")
            .request()
            .method("GET");
        MatcherAssert.assertThat(
            "must be created with status 200",
            response.getStatus(),
            Matchers.is(HttpURLConnection.HTTP_OK)
        );
    }

    @Test
    void gameByIdContainsCorrectJsonStatus() {
        final Response create = this.client
            .target(this.url)
            .path("games")
            .request()
            .method("POST");
        final Response response = this.client
            .target(this.url)
            .path("games/1")
            .request()
            .method("GET");
        MatcherAssert.assertThat(
            "must be created with status 200",
            response.readEntity(JsonObject.class).toString(),
            Matchers.is("{\"id\":1,\"status\":{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6,\"7\":0,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0}}")
        );
    }

    @Test
    void playsExactPit() {
        final Response create = this.client
            .target(this.url)
            .path("games")
            .request()
            .method("POST");
        MatcherAssert.assertThat(
            this.client
                .target(this.url)
                .path("games/1/pits/2")
                .request()
                .put(Entity.json(""))
                .getStatus(),
            Matchers.is(HttpURLConnection.HTTP_ACCEPTED)
        );
        MatcherAssert.assertThat(
            "board must be changed",
            this.client
                .target(this.url)
                .path("games/1")
                .request()
                .method("GET")
                .readEntity(JsonObject.class).toString(),
            Matchers.is("{\"id\":1,\"status\":{\"1\":6,\"2\":0,\"3\":7," +
                "\"4\":7,\"5\":7,\"6\":7,\"7\":1,\"8\":7,\"9\":6,\"10\":6," +
                "\"11\":6,\"12\":6,\"13\":6,\"14\":0}}")
        );
    }

    @AfterEach
    void destroyClass() {
        final CDI<Object> current = CDI.current();
        ((SeContainer) current).close();
    }
}
