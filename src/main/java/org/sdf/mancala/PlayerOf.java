package org.sdf.mancala;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A player with a name.
 *
 * Guarantees equality.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PlayerOf implements Player {
    /**
     * Name of a player.
     */
    private final String name;
}
