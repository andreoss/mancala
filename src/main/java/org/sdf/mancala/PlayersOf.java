package org.sdf.mancala;

import java.util.ArrayDeque;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.cactoos.list.ListOf;

/**
 * Players.
 */
@RequiredArgsConstructor
public final class PlayersOf implements Players {
    /**
     * Queue of players.
     */
    private final Queue<Player> players;

    /**
     * Ctor.
     * @param plyrs Players in this game.
     */
    public PlayersOf(final Player... plyrs) {
        this(new ArrayDeque<>(new ListOf<>(plyrs)));
    }

    @Override
    public Player current() {
        return this.players.peek();
    }

    @Override
    public void turn() {
        this.players.add(this.players.remove());
    }
}
