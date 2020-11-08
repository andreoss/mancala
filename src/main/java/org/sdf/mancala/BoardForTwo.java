package org.sdf.mancala;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterable.RangeOf;
import org.cactoos.list.ListOf;

/**
 * Board for two players.
 *
 * Two player can play this board.
 * Each has a house, which is empty at start.
 */
@RequiredArgsConstructor
public final class BoardForTwo implements Board {

    /**
     * Initial amount of marbles.
     */
    private static final int AMOUNT = 4;

    /**
     * Amount of pits (houses included).
     */
    private static final int PITS = 14;

    /**
     * The pits.
     */
    private final List<Pit> pits;

    /**
     * Default ctor.
     */
    public BoardForTwo() {
        this(AMOUNT, PITS);
    }

    /**
     * Ctor.
     * @param marbles Amount of marbles in each pit.
     * @param pts Amount of pits (including houses).
     */
    public BoardForTwo(final int marbles, final int pts) {
        this(
            new ListOf<>(
                new Joined<>(
                    new Mapped<>(
                        x -> new PitOf(x, marbles),
                        new RangeOf<>(
                            0, pts / 2 - 2, (Integer x) -> x + 1
                        )
                    ),
                    new IterableOf<>(
                        new PitOf(pts - 2 - 1, 0)
                    ),
                    new Mapped<>(
                        x -> new PitOf(x + pts / 2, marbles),
                        new RangeOf<>(
                            0, pts / 2 - 2, (Integer x) -> x + 1
                        )
                    ),
                    new IterableOf<>(
                        new PitOf(pts - 1, 0)
                    )
                )
            )
        );
    }

    @Override
    public int size() {
        return this.pits.size();
    }

    @Override
    public Pit pit(final int position) {
        return this.pits.get(position % this.size());
    }

    @Override
    public boolean isHouse(final int position) {
        return position == (this.size() / 2 - 1)
            || position == (this.size() - 1);
    }

    @Override
    public Pit oppositePit(final int pit) {
        return this.pit(this.complimentIndex(pit));
    }

    /**
     * Index of opposite pit.
     * @param pit a position of pit
     * @return an opposite pit index
     */
    private int complimentIndex(final int pit) {
        return (this.size() - pit - 2) % this.size();
    }

    @Override
    public Pit house(final int pit) {
        final Pit result;
        if (pit < this.size() / 2) {
            result = this.pit(this.size() / 2 - 1);
        } else {
            result = this.pit(this.size() - 1);
        }
        return result;
    }

    @Override
    public Pit oppositeHouse(final int pit) {
        return this.house(this.complimentIndex(pit));
    }

    @Override
    public Iterable<Pit> pitsOnSide(final int pit) {
        final Iterable<Pit> result;
        if (pit < this.size() / 2) {
            result = this.pits.subList(0, this.size() / 2 - 1);
        } else {
            result = this.pits.subList(this.size() / 2, this.size() - 1);
        }
        return result;
    }

    @Override
    public Iterable<Pit> pitsOnOppositeSide(final int pit) {
        return this.pitsOnSide(this.complimentIndex(pit));
    }
}
