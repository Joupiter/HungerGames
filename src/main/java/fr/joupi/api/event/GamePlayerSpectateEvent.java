package fr.joupi.api.event;

import fr.joupi.api.MiniGame;
import fr.joupi.api.player.GamePlayer;

public class GamePlayerSpectateEvent<M extends MiniGame, G extends GamePlayer<?>> extends GamePlayerEvent<M, G> {

    public GamePlayerSpectateEvent(M game, G gamePlayer) {
        super(game, gamePlayer);
    }

}