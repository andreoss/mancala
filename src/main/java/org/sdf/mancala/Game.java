package org.sdf.mancala;

/**
 * Game.
 */
public interface Game {
    /**
     * Game id.
     * @return Id number
     */
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

    /**
     * Is game finished?
     * @return true if yes
     */
    boolean isFinished();

    /**
     * Players of this game.
     * @return players
     **/
    Players players();
}
