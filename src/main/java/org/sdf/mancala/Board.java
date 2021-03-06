package org.sdf.mancala;

/**
 * Mancala's board.
 */
public interface Board {
    /**
     * Size of the board.
     *
     * @return Number of pits.
     */
    int size();

    /**
     * Pit at given position.
     * @param position Position of a pit (started from zero).
     * @return The pit
     */
    Pit pit(int position);

    /**
     * Is this pit a house (i.e mancala).
     * @param position Position of a pit
     * @return true if it is a house
     */
    boolean isHouse(int position);

    /**
     * Pit on the opposite side.
     * @param pit Pit position.
     * @return Opposite pit to the position.
     */
    Pit oppositePit(int pit);

    /**
     * Closest house to a pit.
     * @param pit Position of a pit
     * @return House pit
     */
    Pit house(int pit);

    /**
     * Closest opposite house to a pit.
     * @param pit Position of a pit
     * @return Opposite house pit
     */
    Pit oppositeHouse(int pit);

    /**
     * All pits on player's side.
     * @param pit Position of a pit
     * @return Collections of pits (house excluded).
     */
    Iterable<Pit> pitsOnSide(int pit);

    /**
     * All pits on opponent's side.
     * @param pit Position of a pit
     * @return Collections of pits (house excluded).
     */
    Iterable<Pit> pitsOnOppositeSide(int pit);
}
