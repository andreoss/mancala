package org.sdf.mancala;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GameOf}.
 *
 */
final class GameOfTest {
    @Test
    void boardBeforeGameStarted() {
        final Game game = new GameOf(
            0, new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 4, 4, 4, 4, 4, 0,
                4, 4, 4, 4, 4, 4, 0
            )
        );
    }

    @Test
    void playerOneMakesMove() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 0);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                0, 5, 5, 5, 5, 4, 0,
                4, 4, 4, 4, 4, 4, 0
            )
        );
    }

    @Test
    void playerOneMakesMovePassesHouse() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 4, 0, 5, 5, 5, 1,
                4, 4, 4, 4, 4, 4, 0
            )
        );
    }


    @Test
    void lastStoneLandedInMancala() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        MatcherAssert.assertThat(
            "you get an extra turn if last stone landed in mancala",
            game.players().current(),
            Matchers.is(new PlayerOf("a"))
        );
    }

    @Test
    void twoMovesByPlayerA() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 1, 6, 6, 6, 1,
                4, 4, 4, 4, 4, 4, 0
            )
        );
    }

    @Test
    void twoMovesByAAndAMoveByB() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 1, 6, 6, 6, 1,
                4, 4, 0, 5, 5, 5, 1
            )
        );
    }

    @Test
    void twoMovesByAAndTwoMovesByB() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 1, 6, 6, 6, 1,
                0, 5, 1, 6, 6, 5, 1
            )
        );
    }

    @Test
    void twoMovesByAAndTwoMovesByBAndAMoveByA() {
        final Game game = new GameOf(
            0,
            new BoardForTwo(),
            new PlayersOf(
                new PlayerOf("a"),
                new PlayerOf("b")
            )
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 1, 6, 6, 6, 1,
                0, 5, 1, 6, 6, 5, 1
            )
        );
    }

    @Test
    void extraTurn() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(),
            players
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        game.play(new PlayerOf("a"), 3);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 1, 0, 7, 7, 2,
                1, 6, 2, 6, 6, 5, 1
            )
        );
    }


    @Test
    void whenPieceLandsInAnEmptyPit() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(),
            players
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        game.play(new PlayerOf("a"), 3);
        players.turn();
        game.play(new PlayerOf("a"), 2);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                4, 0, 0, 0, 7, 7, 5,
                1, 6, 0, 6, 6, 5, 1
            )
        );
    }

    @Test
    void whenGamePlayedTo_() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(),
            players
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        game.play(new PlayerOf("a"), 3);
        players.turn();
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("b"), 10);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                5, 1, 1, 0, 7, 7, 5,
                1, 6, 0, 0, 7, 6, 2
            )
        );
    }

    @Test
    void whenGamePlayedTo_2() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(),
            players
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        game.play(new PlayerOf("a"), 3);
        players.turn();
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("b"), 10);
        game.play(new PlayerOf("a"), 4);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                5, 1, 1, 0, 0, 8, 6,
                2, 7, 1, 1, 8, 6, 2
            )
        );
    }

    @Test
    void whenGamePlayedTo_3() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(),
            players
        );
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("a"), 1);
        game.play(new PlayerOf("b"), 9);
        game.play(new PlayerOf("b"), 7);
        game.play(new PlayerOf("a"), 3);
        players.turn();
        game.play(new PlayerOf("a"), 2);
        game.play(new PlayerOf("b"), 10);
        game.play(new PlayerOf("a"), 4);
        game.play(new PlayerOf("b"), 12);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                6, 2, 2, 1, 0, 8, 6,
                2, 0, 1, 1, 8, 0, 11
            )
        );
    }

    @Test
    void playerBHasNoMarblesOnHisSide() {
        final var players = new PlayersOf(
            new PlayerOf("a"),
            new PlayerOf("b")
        );
        final var game = new GameOf(
            0,
            new BoardForTwo(
                List.of(
                    new PitOf(0, 2), new PitOf(1, 2), new PitOf(2, 2),
                    new PitOf(3, 2), new PitOf(4, 1), new PitOf(5, 1),
                    new PitOf(6, 16), new PitOf(7, 0), new PitOf(8, 0),
                    new PitOf(9, 0), new PitOf(10, 0), new PitOf(11, 0),
                    new PitOf(12, 1), new PitOf(13, 9)
                )
            ),
            players
        );
        players.turn();
        game.play(new PlayerOf("b"), 12);
        MatcherAssert.assertThat(
            game.board(),
            new BoardIs(
                0, 0, 0, 0, 0, 0, 26,
                0, 0, 0, 0, 0, 0, 10
            )
        );
        MatcherAssert.assertThat(
            game.isFinished(),
            Matchers.is(true)
        );
    }
}