package org.sdf.mancala;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class BoardForTwoTest {
    @Test
    void getsSize() {
        final var board = new BoardForTwo();
        MatcherAssert.assertThat(
            board.size(),
            Matchers.is(14)
        );
    }

    @Test
    void getsAdjacentPit() {
        final var board = new BoardForTwo();
        MatcherAssert.assertThat(
            board.oppositePit(0),
            Matchers.is(
                board.pit(12)
            )
        );
    }

    @ParameterizedTest
    @CsvSource(
        value = {
            "0, 12",
            "1, 11",
            "2, 10",
            "3, 9",
            "4, 8",
            "5, 7",
        }
    )
    void getsAdjecentPitReverse(
        final int pit, final int comp
    ) {
        final var board = new BoardForTwo();
        MatcherAssert.assertThat(
            board.oppositePit(pit),
            Matchers.is(
                board.pit(comp)
            )
        );
        MatcherAssert.assertThat(
            board.pit(pit),
            Matchers.is(
                board.oppositePit(comp)
            )
        );
    }

    @Test
    void getsClosestHouse() {
        final var board = new BoardForTwo();
        MatcherAssert.assertThat(
            board.house(0),
            Matchers.is(
                board.pit(6)
            )
        );
    }

    @Test
    void getsClosestHouseForSecondPlayer() {
        final var board = new BoardForTwo();
        MatcherAssert.assertThat(
            board.house(7),
            Matchers.is(
                board.pit(13)
            )
        );
    }


    @Test
    void onSmallerBoard() {
        final var board = new BoardForTwo(6, 8);
        MatcherAssert.assertThat(
            board.isHouse(7),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            board.isHouse(3),
            Matchers.is(true)
        );
    }


    @Test
    void pitsOnFirstSide() {
        final var board = new BoardForTwo(6, 6);
        MatcherAssert.assertThat(
            board.pitsOnSide(0),
            Matchers.contains(
                board.pit(0),
                board.pit(1)
            )
        );
    }

    @Test
    void pitsOnSecondSide() {
        final var board = new BoardForTwo(6, 6);
        MatcherAssert.assertThat(
            board.pitsOnSide(3),
            Matchers.contains(
                board.pit(3),
                board.pit(4)
            )
        );
    }
}