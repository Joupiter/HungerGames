package fr.joupi.api.event;

import fr.joupi.api.MiniGame;
import fr.joupi.api.player.GamePlayer;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public abstract class GamePlayerEvent<M extends MiniGame, G extends GamePlayer<?>> extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    protected final M game;
    protected final G gamePlayer;

    public GamePlayerEvent(M game, G gamePlayer) {
        super(gamePlayer.getPlayer());
        this.game = game;
        this.gamePlayer = gamePlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}