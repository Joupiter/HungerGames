package fr.joupi.api;

import fr.joupi.api.player.GamePlayer;
import fr.joupi.api.team.GameTeam;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface MiniGame {

    Logger log = LoggerFactory.getLogger(MiniGame.class);

    String getName();
    String getFullName();

    String getId();

    GameState getState();
    GameSize getGameSize();

    <G extends GamePlayer<T>, T extends GameTeam<G>> ConcurrentMap<UUID, G> getPlayers();
    <G extends GamePlayer<T>, T extends GameTeam<G>> List<T> getTeams();

    <S extends GameSettings> S getSettings();

    void preload();
    void load();

    void unload();
    void endGame();

    void joinGame(Player player, boolean spectator);

    default void joinGame(Player player) {
        joinGame(player, false);
    }

    void leaveGame(UUID uuid);

    boolean containsPlayer(UUID uuid);
    boolean containsPlayer(Player player);

    void broadcast(String message);
    void broadcast(String... message);

    void setState(GameState gameState);

    boolean canStart();
    boolean isFull();
    boolean canJoin();

    int getAlivePlayersCount();
    int getSpectatorsCount();
    int getSize();

    void sendDebugInfoMessage(CommandSender sender);

}