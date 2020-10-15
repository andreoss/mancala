package org.sdf.mancala;

/**
 * All games.
 */
public interface Games {
    /**
     * Start a new one.
     * @return a new game
     */
    Game start();

    /**
     * Find by id.
     * @param id Id of a game.
     * @return a found game
     */
    Game find(int id);

    /**
     * Is there a game with such id.
     * @param id Id of a game
     * @return true if there is one
     */
    boolean exists(int id);
}
