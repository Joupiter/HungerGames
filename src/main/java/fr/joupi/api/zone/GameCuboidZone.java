package fr.joupi.api.zone;

import fr.joupi.api.MiniGame;
import fr.joupi.api.player.GamePlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class GameCuboidZone<M extends MiniGame, G extends GamePlayer<?>> implements CuboidZone {

    protected M game;

    protected Vector center, topCorner, botCorner;
    protected Set<G> players;

    public GameCuboidZone(M game, Location center, int size) {
        var newSize = size / 2.0;

        this.game = game;
        this.center = center.toVector();
        this.topCorner = center.toVector().add(new Vector(newSize, newSize, newSize));
        this.botCorner = center.toVector().subtract(new Vector(newSize, newSize, newSize));
        this.players = new HashSet<>(game.getSettings().getGameSize().getMaxPlayer());
    }

    protected abstract void onEnter(G gamePlayer);
    protected abstract void onExit(G gamePlayer);

    @Override
    public void showBorder(GamePlayer<?> player) {
        var world = Bukkit.getWorld("world");
        double y = botCorner.getY();

        double minX = botCorner.getX();
        double maxX = topCorner.getX();
        double minZ = botCorner.getZ();
        double maxZ = topCorner.getZ();

        double step = 1.0;

        for (double x = minX; x <= maxX; x += step) {
            world.playEffect(new Location(world, x, y, minZ), Effect.COLOURED_DUST, 0);
            world.playEffect(new Location(world, x, y, maxZ), Effect.COLOURED_DUST, 0);
        }

        for (double z = minZ; z <= maxZ; z += step) {
            world.playEffect(new Location(world, minX, y, z), Effect.COLOURED_DUST, 0);
            world.playEffect(new Location(world, maxX, y, z), Effect.COLOURED_DUST, 0);
        }
    }

    @Override
    public void reduce(double factor) {
        var center = botCorner.clone().add(topCorner).multiply(0.5);

        botCorner.add(center.clone().subtract(botCorner).multiply(factor));
        topCorner.add(center.clone().subtract(topCorner).multiply(factor));

        if (topCorner.getX() <= botCorner.getX()
                || topCorner.getY() <= botCorner.getY()
                || topCorner.getZ() <= botCorner.getZ()) {

            this.botCorner = topCorner.clone();
        }
    }

    @Override
    public void reduce(int block) {
        reduce(block / (topCorner.getX() - botCorner.getX()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addPlayer(GamePlayer<?> gamePlayer) {
        if (players.add((G) gamePlayer))
            onEnter((G) gamePlayer); // CALL OBSERVER
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removePlayer(GamePlayer<?> gamePlayer) {
        if (players.removeIf(gamePlayer::equals))
            onExit((G) gamePlayer); // CALL OBSERVER
    }

    @Override
    public double getVolume() {
        var width = topCorner.getX() - botCorner.getX();
        var height = topCorner.getY() - botCorner.getY();
        var depth = topCorner.getZ() - botCorner.getZ();

        return Math.max(0, width * height * depth);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(GamePlayer<?> gamePlayer) {
        return players.contains((G) gamePlayer);
    }

    @Override
    public boolean contains(Location location) {
        return location.toVector().isInAABB(botCorner, topCorner);
    }

    @Override
    public boolean isActive() {
        return getVolume() > 0;
    }

}