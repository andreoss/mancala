package org.sdf.mancala;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pit on board.
 */
public final class PitOf implements Pit {

    /**
     * Position of a pit.
     */
    private final int id;

    /**
     * Marbles inside.
     */
    private final AtomicInteger cnt;

    /**
     * Ctor.
     * @param ident Position
     * @param count Marble count
     */
    public PitOf(final int ident, final AtomicInteger count) {
        this.id = ident;
        this.cnt = count;
    }

    /**
     * Ctor.
     * @param position Position.
     * @param marbles Number of marbles.
     */
    public PitOf(final int position, final int marbles) {
        this(position, new AtomicInteger(marbles));
    }

    @Override
    public int count() {
        return this.cnt.get();
    }

    @Override
    public void put() {
        this.cnt.incrementAndGet();
    }

    @Override
    public void takeAll() {
        this.cnt.set(0);
    }

    @Override
    public void putFrom(final Pit pit) {
        final int marbles = pit.count();
        pit.takeAll();
        this.cnt.set(this.cnt.get() + marbles);
    }

    @Override
    public String toString() {
        return "PitOf(id=" + this.id + ", cnt=" + this.cnt + ")";
    }
}
