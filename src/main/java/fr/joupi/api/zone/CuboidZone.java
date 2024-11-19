package fr.joupi.api.zone;

import fr.joupi.hg.player.HungerPlayer;
import org.bukkit.util.Vector;

public interface CuboidZone {

    Vector getCenter();
    Vector getBotCorner();
    Vector getTopCorner();

    void onEnter(HungerPlayer hungerPlayer);
    void onExit(HungerPlayer hungerPlayer);

    void onStart();
    void onNext(CuboidZone zone);
    void onEnd();

    void showBorder(HungerPlayer player);

    void reduce(double factor);
    void reduce(int block);

    double getVolume();

    boolean contains(HungerPlayer hungerPlayer);
    boolean isActive();

}