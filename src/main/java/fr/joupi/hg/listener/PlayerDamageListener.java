package fr.joupi.hg.listener;

import fr.joupi.hg.HungerGame;
import fr.joupi.hg.HungerGamesPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PlayerDamageListener {

    private final HungerGamesPlugin plugin;
    private final HungerGame game;

    public PlayerDamageListener(HungerGamesPlugin plugin) {
        this.plugin = plugin;
        this.game = plugin.getGame();
    }

    @EventHandler
    public void on() {}

}