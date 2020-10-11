package org.sdf.mancala;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@RequiredArgsConstructor
public final class BoardIs extends TypeSafeDiagnosingMatcher<Board> {
    private final List<Matcher<Pit>> pits;

    public BoardIs(
        final int... sizes
    ) {
        this(
            IntStream.of(sizes)
                .mapToObj(CountIs::new)
                .collect(Collectors.toList())
        );
    }

    @Override
    protected boolean matchesSafely(
        final Board board, final Description description
    ) {
        boolean result = true;
        for (int i = 0; i < this.pits.size(); i++) {
            this.pits.get(i).describeMismatch(
                board.pit(i), description
            );
            result = result && this.pits.get(i).matches(board.pit(i));
        }
        return result;
    }

    @Override
    public void describeTo(final Description description) {
        this.pits.forEach(description::appendDescriptionOf);
    }
}
