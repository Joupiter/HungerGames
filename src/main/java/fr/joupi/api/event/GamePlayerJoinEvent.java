package fr.joupi.api.event;

import fr.joupi.api.MiniGame;
import fr.joupi.api.player.GamePlayer;

public class GamePlayerJoinEvent<M extends MiniGame, G extends GamePlayer<?>> extends GamePlayerEvent<M, G> {

    public GamePlayerJoinEvent(M game, G gamePlayer) {
        super(game, gamePlayer);
    }

}