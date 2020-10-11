package org.sdf.mancala;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PlayersOf}.
 */
final class PlayersOfTest {
    @Test
    void fourPlayersTurnSides() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b"),
            new PlayerOf("c"),
            new PlayerOf("d")
        );
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(new PlayerOf("a"))
        );
        players.turn();
        players.turn();
        players.turn();
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(new PlayerOf("d"))
        );
        players.turn();
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(new PlayerOf("a"))
        );
    }

    @Test
    void twoPlayersTurnSides() {
        final var fst = new PlayerOf("1");
        final var snd = new PlayerOf("2");
        final var players = new PlayersOf(fst, snd);
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(fst)
        );
        players.turn();
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(snd)
        );
        players.turn();
        MatcherAssert.assertThat(
            players.current(),
            Matchers.is(fst)
        );
    }
}