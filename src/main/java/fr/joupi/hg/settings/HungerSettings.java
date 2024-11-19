package fr.joupi.hg.settings;

import fr.joupi.api.GameSettings;
import fr.joupi.api.GameSize;
import fr.joupi.api.map.GameMap;
import fr.joupi.api.map.Map;
import fr.joupi.api.utils.EnumDirection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
public class HungerSettings extends GameSettings {

    private final Map waitingMap, arenaMap;

    private int startTimer;

    private Location safeZoneCenter;
    private int safeZoneSize, safeZoneReduceTime, safeZoneReduceBlock;

    public HungerSettings(GameSize gameSize) {
        super(gameSize);
        this.waitingMap = new GameMap("waiting"/*, "lobby", 0, 80, 0, EnumDirection.NORTH*/);
        this.arenaMap = new GameMap("arena", "hgmap", 0, 80, 0, EnumDirection.NORTH);
        this.startTimer = 4; // JUST AN RANDOM VALUE
        this.safeZoneCenter = new Location(Bukkit.getWorld("world"), 0, 6, 0);
        this.safeZoneSize = 10; // SIZE OF THE SAFEZONE IN BLOCKS
        this.safeZoneReduceTime = 2; // TIME FOR REDUCE THE SAFEZONE (in seconds)
        this.safeZoneReduceBlock = 1; // 1 BLOCK PER safeZoneReduceTime
    }

}