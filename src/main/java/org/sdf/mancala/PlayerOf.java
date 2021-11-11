package org.sdf.mancala;

import java.util.Objects;

/**
 * A player with a name.
 *
 * Guarantees equality.
 */
public class PlayerOf implements Player {
    /**
     * Name of a player.
     */
    private final String name;

    /**
     * Ctor.
     * @param nm Name
     */
    public PlayerOf(final String nm) {
        this.name = nm;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final PlayerOf other = (PlayerOf) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return "PlayerOf(name=" + this.name + ")";
    }
}
