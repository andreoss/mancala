package org.sdf.mancala;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

/**
 * Board for two players
 */
@RequiredArgsConstructor
public class BoardForTwo implements Board {
    private final List<Pit> pits;


    /**
     * Default ctor.
     */
    public BoardForTwo() {
        this(4, 14);
    }

    /**
     * Ctor.
     * @param amount Amount of marbles in each pit.
     * @param pits Amount of pits (including houses).
     */
    public BoardForTwo(final int amount, final int pits) {
        this(
            Stream.of(
                IntStream
                    .range(0, pits / 2 - 1)
                    .mapToObj(x -> new PitOf(x, amount)),
                Stream.of(new PitOf(pits - 2 - 1, 0)),
                IntStream
                    .range(0, pits / 2 - 1)
                    .mapToObj(x -> new PitOf(x + pits / 2, amount)),
                Stream.of(new PitOf(pits - 1, 0))
            ).flatMap(Function.identity()).collect(Collectors.toList())
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
        return position == (this.size() / 2 - 1) || position == (this.size() - 1);
    }

    @Override
    public Pit oppositePit(final int pit) {
        return this.pit(this.complimentIndex(pit));
    }

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
    public Collection<Pit> pitsOnSide(final int pit) {
        final Collection<Pit> result;
        if (pit < this.size() / 2) {
            result = this.pits.subList(0, this.size() / 2 - 1);
        } else {
            result = this.pits.subList(this.size() / 2, this.size() - 1);
        }
        return result;
    }

    @Override
    public Collection<Pit> pitsOnOppositeSide(final int pit) {
        return this.pitsOnSide(this.complimentIndex(pit));
    }
}
