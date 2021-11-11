package org.sdf.mancala;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PlayerOf}.
 */
final class PlayerOfTest {
    @Test
    void shouldBeEqualToItself() {
        MatcherAssert.assertThat(
            new PlayerOf("a"),
            Matchers.allOf(
                Matchers.is(new PlayerOf("a")),
                Matchers.not(new PlayerOf("b"))
            )
        );
    }
}