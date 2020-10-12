package org.sdf.mancala;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.Config;

@ApplicationScoped
public final class InMemoryGames implements Games {
    private final AtomicInteger count = new AtomicInteger();

    private final Map<Integer, Game> games = new HashMap<>();

    private final Config config;

    @Inject
    public InMemoryGames(final Config config) {
        this.config = config;
    }

    @Override
    public Game start() {
        final int id = this.count.incrementAndGet();
        final Game game = new GameOf(
            id,
            new BoardForTwo(
                this.config.getValue("mancala.initialMarbles", Integer.class),
                this.config.getValue("mancala.boardSize", Integer.class)
            ),
            new PlayersOf(
                new PlayerOf("first"),
                new PlayerOf("second")
            )
        );
        this.games.put(id, game);
        return game;
    }

    @Override
    public Game find(final int id) {
        return Objects.requireNonNull(this.games.get(id));
    }

    @Override
    public boolean exists(final int id) {
        return this.games.containsKey(id);
    }
}
