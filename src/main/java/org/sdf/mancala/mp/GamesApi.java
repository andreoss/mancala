package org.sdf.mancala.mp;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/games")
public interface GamesApi {
    Response findById(int id);

    Response play(int gid, int pid);

    Response startGame();
}
