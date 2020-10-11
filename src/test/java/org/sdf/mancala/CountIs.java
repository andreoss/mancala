package org.sdf.mancala;

import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@RequiredArgsConstructor
public final class CountIs extends TypeSafeDiagnosingMatcher<Pit> {
    private final int count;

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
