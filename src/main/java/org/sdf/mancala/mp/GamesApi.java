package org.sdf.mancala.mp;

import javax.ws.rs.core.Response;

public interface GamesApi {
    Response findById(int id);

    Response play(int gid, int pid);

    Response startGame();
}
