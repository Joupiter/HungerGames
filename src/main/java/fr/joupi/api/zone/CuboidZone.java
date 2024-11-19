package fr.joupi.api.zone;

import fr.joupi.api.player.GamePlayer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public interface CuboidZone {

    Vector getCenter();
    Vector getBotCorner();
    Vector getTopCorner();

    <G extends GamePlayer<?>> Set<G> getPlayers();

    void onStart();
    void onNext(CuboidZone zone);
    void onEnd();

    void showBorder(GamePlayer<?> player);

    void reduce(double factor);
    void reduce(int block);

    void addPlayer(GamePlayer<?> gamePlayer);
    void removePlayer(GamePlayer<?> gamePlayer);

    double getVolume();

    boolean contains(GamePlayer<?> gamePlayer);
    boolean contains(Location location);

    boolean isActive();

}