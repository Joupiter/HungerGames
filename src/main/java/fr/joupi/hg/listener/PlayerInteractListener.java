package fr.joupi.hg.listener;

import fr.joupi.hg.HungerGame;
import fr.joupi.hg.waiting.HungerWaitingItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public record PlayerInteractListener(HungerGame game) implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var itemStack = event.getItem();

        if (itemStack == null || !itemStack.hasItemMeta()) return;

        var player = event.getPlayer();
        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        game.sendDebugInfoMessage(player);

        switch (game.getState()) {
            case WAIT, STARTING -> HungerWaitingItems.getWaitingItem(itemStack).ifPresent(item -> item.getConsumer().accept(game, gamePlayer));
            case END -> {} // ??!!
        }

        event.setCancelled(true);
    }

}