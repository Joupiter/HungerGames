package fr.joupi.hg.listener;

import fr.joupi.api.GameState;
import fr.joupi.hg.HungerGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public record PlayerMoveListener(HungerGame game) implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        var gamePlayer = game.getNullablePlayer(player);
        var location = event.getTo();

        if (gamePlayer == null
                || gamePlayer.isSpectator()
                || game.getState() != GameState.IN_GAME) return;

        var zone = game.getSafeZone();

        if (zone.contains(location))
            zone.addPlayer(gamePlayer);
        else
            zone.removePlayer(gamePlayer);
    }

}