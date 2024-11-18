package fr.joupi.hg;

import fr.joupi.api.utils.concurrent.BukkitThreading;
import fr.joupi.hg.listener.PlayerConnectionListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class HungerGamesPlugin extends JavaPlugin {

    private HungerGame game;

    @Override
    public void onEnable() {
        BukkitThreading.setPlugin(this);

        this.game = new HungerGame();

        registerListeners(
                new PlayerConnectionListener(this, game)
        );

    }

    private void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(this::registerListener);
    }

    @Override
    public void onDisable() {}

}