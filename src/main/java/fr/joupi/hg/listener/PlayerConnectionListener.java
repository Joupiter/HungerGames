package fr.joupi.hg.listener;

import fr.joupi.api.GameState;
import fr.joupi.api.event.GamePlayerJoinEvent;
import fr.joupi.api.event.GamePlayerLeaveEvent;
import fr.joupi.api.event.GamePlayerSpectateEvent;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.HungerGamesPlugin;
import fr.joupi.hg.player.HungerPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerConnectionListener(HungerGamesPlugin plugin, HungerGame game) implements Listener {

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        var player = event.getPlayer();

        if (game == null
                || game.getState() == GameState.LOADING
                || game.getState() == GameState.LOADING_IN_PROGRESS) {

            player.sendMessage("Sorry, no game available...");
            return;
        }

        game.joinGame(player, game.getState().is(GameState.IN_GAME, GameState.END));
    }

    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        var uuid = event.getPlayer().getUniqueId();

        game.leaveGame(uuid);
    }

    @EventHandler
    public void onJoinGame(GamePlayerJoinEvent<HungerGame, HungerPlayer> event) {

    }

    @EventHandler
    public void onSpectate(GamePlayerSpectateEvent<HungerGame, HungerPlayer> event) {

    }

    @EventHandler
    public void onLeaveGame(GamePlayerLeaveEvent<HungerGame, HungerPlayer> event) {

    }

}