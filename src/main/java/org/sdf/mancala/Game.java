package org.sdf.mancala;

/**
 * Game.
 */
public interface Game {
    int id();

    /**
     * Do a play as one of the players.
     * @param player Player
     * @param pit Pit to play
     */
    void play(Player player, int pit);

    /**
     * Board of the game.
     * @return Board
     */
    Board board();

    boolean isFinished();

    Players players();
}


