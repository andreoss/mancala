package org.sdf.mancala;

/**
 * A pit on a board.
 *
 */
public interface Pit {
    /**
     * Marbles inside.
     *
     * @return number of marbles.
     */
    int count();

    /**
     * Put one marble in.
     */
    void put();

    /**
     * Take all marbles out.
     */
    void takeAll();

    /**
     * Put all marbles from pit.
     * Empties pit after that.
     * @param pit Source pit.
     */
    void putFrom(Pit pit);
}
