package org.sdf.mancala;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;
import org.cactoos.number.SumOf;

/**
 * Game.
 */
@RequiredArgsConstructor
public final class GameOf implements Game {
    /**
     * Id.
     */
    private final int id;

    /**
     * Board.
     */
    private final Board board;

    /**
     * Players.
     */
    private final Players players;

    /**
     * Flag for game status.
     */
    private final AtomicBoolean finished = new AtomicBoolean(false);

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void play(final Player player, final int pit) {
        if (!this.players.current().equals(player)) {
            throw new IllegalStateException(
                String.format("it's %s turn now", player)
            );
        }
        if (this.isFinished()) {
            throw new IllegalStateException("game is finished");
        }
        if (this.board.isHouse(pit)) {
            throw new IllegalStateException(
                String.format("%d is a house", pit)
            );
        }
        final Pit played = this.board.pit(pit);
        final int count = played.count();
        played.takeAll();
        final Pit last = this.board.pit(this.shift(pit, count));
        final boolean lastInEmptyPit = last.count() == 0;
        for (int i = 1; i <= count; i++) {
            this.board.pit(this.shift(pit, i)).put();
        }
        final Pit house = this.board.house(pit);
        if (!this.board.isHouse(pit + count)) {
            this.players.turn();
            if (lastInEmptyPit) {
                final Pit adjacent = this.board.oppositePit(
                    this.shift(pit, count)
                );
                if (adjacent.count() > 0) {
                    house.putFrom(last);
                    house.putFrom(adjacent);
                }
            }
        }
        final Iterable<Pit> mine = this.board.pitsOnSide(pit);
        if (new SumOf(new Mapped<>(Pit::count, mine)).intValue() == 0) {
            this.board.pitsOnOppositeSide(pit).forEach(
                this.board.oppositeHouse(pit)::putFrom
            );
            this.finished.set(true);
        }
    }

    /**
     * Shift position.
     * @param pit Start from this pit.
     * @param count Positions to shift.
     * @return Position
     */
    private int shift(final int pit, final int count) {
        return (pit + count) % this.board.size();
    }

    @Override
    public Board board() {
        return this.board;
    }

    @Override
    public boolean isFinished() {
        return this.finished.get();
    }

    @Override
    public Players players() {
        return this.players;
    }
}
