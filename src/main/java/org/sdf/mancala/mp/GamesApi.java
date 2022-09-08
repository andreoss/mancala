package org.sdf.mancala.mp;

import jakarta.ws.rs.core.Response;

/**
 * API for the game.
 */
public interface GamesApi {
    /**
     * Find a game by id.
     * @param id Id of a game.
     * @return HTTP response.
     */
    Response findById(int id);

    /**
     * Play a pit in some game.
     * @param gid Id of game.
     * @param pid Id of pit.
     * @return HTTP response.
     */
    Response play(int gid, int pid);

    /**
     * Start a new game.
     * @return HTTP response.
     */
    Response startGame();
}
