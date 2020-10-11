package org.sdf.mancala;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public final class PitOf implements Pit {

    private final int id;

    private final AtomicInteger cnt;

    public PitOf(final int id, final int marbles) {
        this(id, new AtomicInteger(marbles));
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
}
