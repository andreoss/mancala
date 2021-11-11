package org.sdf.mancala;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class CountIs extends TypeSafeDiagnosingMatcher<Pit> {
    private final int count;

    /**
     * Ctor.
     * @param cnt Count to match
     */
    public CountIs(final int cnt) {
        this.count = cnt;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(this.count);
    }

    @Override
    protected boolean matchesSafely(
        final Pit pit, final Description description
    ) {
        description.appendValue(pit.count());
        return this.count == pit.count();
    }
}
