package org.sdf.mancala;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class InMemoryGames implements Games {
    private final AtomicInteger count = new AtomicInteger();

    private final Map<Integer, Game> games = new HashMap<>();

    @Override
    public Game start() {
        final int id = this.count.incrementAndGet();
        final Game game = new GameOf(
            id,
            new BoardForTwo(),
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
