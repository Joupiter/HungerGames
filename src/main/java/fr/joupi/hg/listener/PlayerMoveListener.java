package fr.joupi.hg.listener;

import fr.joupi.hg.HungerGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public record PlayerMoveListener(HungerGame game) implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // TRIGGER SAFE ZONE HERE
    }

}