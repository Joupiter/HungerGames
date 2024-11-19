package fr.joupi.hg.listener;

import fr.joupi.api.player.GamePlayer;
import fr.joupi.hg.HungerGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public record PlayerChatListener(HungerGame game) implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        var player = event.getPlayer();
        var message = event.getMessage();
        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var team = gamePlayer.getTeam();

        switch (game.getState()) {
            case WAIT, STARTING, END ->
                    game.broadcast(team == null
                            ? "§7" + player.getName() + " : §f" + message
                            : team.getColoredName() + " " + player.getName() + " §7: §f" + message);
            case IN_GAME -> {
                if (team == null) {
                    // SPECTATOR
                    game.broadcast(GamePlayer::isSpectator, "§7Spectateur " + player.getName() + " : §f" + message);
                    return;
                }

                if (message.startsWith("@")) {
                    if (message.equals("@")) return;

                    game.broadcast("§7[§b@§7] " + team.getColoredName() + " " + player.getName() + " §7: §f" + message.replaceFirst("@", ""));
                    return;
                }

                team.broadcast(team.getColoredName() + " " + player.getName() + " §7: §f" + message);
            }
        }
    }

}