package org.sdf.mancala;

/**
 * Players in the game.
 */
public interface Players {
    /**
     * Player which plays now.
     * @return A current player
     */
    Player current();

    /**
     * Next turn.
     */
    void turn();
}
