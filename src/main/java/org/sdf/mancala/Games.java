package org.sdf.mancala;

public interface Games {
    Game start();

    Game find(int id);

    boolean exists(int id);
}
