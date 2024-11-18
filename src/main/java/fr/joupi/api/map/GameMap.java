package fr.joupi.api.map;

import fr.joupi.api.utils.EnumDirection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
public class GameMap implements Map {

    private final String name;
    private Location spawn;

    public GameMap(String name, Location location) {
        this.name = name;
        this.spawn = location;
    }

    public GameMap(String name) {
        this(name, null);
    }

    public GameMap(String name, double x, double y, double z, float yaw, float pitch) {
        this(name, new Location(Bukkit.getWorld(name), x, y, z, yaw, pitch));
    }

    public GameMap(String name, String world, double x, double y, double z, float yaw, float pitch) {
        this(name, new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
    }

    public GameMap(String name, double x, double y, double z, EnumDirection direction) {
        this(name, x, y, z, direction.getYaw(), 0);
    }

    public GameMap(String name, String world, double x, double y, double z, EnumDirection direction) {
        this(name, world, x, y, z, direction.getYaw(), 0);
    }

}