package fr.joupi.hg.listener;

import fr.joupi.api.GameState;
import fr.joupi.api.event.GamePlayerJoinEvent;
import fr.joupi.api.event.GamePlayerLeaveEvent;
import fr.joupi.api.event.GamePlayerSpectateEvent;
import fr.joupi.api.utils.NameTag;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;
import fr.joupi.hg.settings.HungerMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerConnectionListener(HungerGame game) implements Listener {

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        var player = event.getPlayer();

        event.setJoinMessage(null);
        NameTag.setNameTag(player, "§7", " ", 999);

        if (game == null || game.getState() == GameState.LOADING) {
            player.sendMessage("Sorry, no game available...");
            return;
        }

        game.joinGame(player, game.getState().is(GameState.IN_GAME, GameState.END));
    }

    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        var uuid = event.getPlayer().getUniqueId();

        event.setQuitMessage(null);

        game.leaveGame(uuid);
    }

    @EventHandler
    public void onJoinGame(GamePlayerJoinEvent<HungerGame, HungerPlayer> event) {
        var player = event.getPlayer();

        game.broadcast(HungerMessages.PLAYER_JOIN_MESSAGE.getMessage(player.getName(), game.getSize(), game.getGameSize().getMaxPlayer()));
    }

    @EventHandler
    public void onSpectate(GamePlayerSpectateEvent<HungerGame, HungerPlayer> event) {
        var player = event.getPlayer();

        game.broadcast(HungerMessages.PLAYER_SPECTATE_MESSAGE.getMessage());
    }

    @EventHandler
    public void onLeaveGame(GamePlayerLeaveEvent<HungerGame, HungerPlayer> event) {
        var player = event.getPlayer();

        game.broadcast(HungerMessages.PLAYER_LEAVE_MESSAGE.getMessage(player.getName(), game.getSize(), game.getGameSize().getMaxPlayer()));
    }

}