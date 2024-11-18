package fr.joupi.hg.player;

import fr.joupi.api.player.GamePlayer;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class HungerPlayer extends GamePlayer<HungerTeam> {

    private int kills;

    // USE INSTANT OR JUST AN CLASSIC LONG AND NEED TO BE SET WHEN THE PLAYER DIE OR WIN OR LEAVE
    private Instant surviveTime;

    public HungerPlayer(UUID uuid, boolean spectator) {
        super(uuid, spectator);
        this.kills = 0;
    }

}