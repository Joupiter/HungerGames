package fr.joupi.api.waiting;

import fr.joupi.api.MiniGame;
import fr.joupi.api.map.Map;
import fr.joupi.api.player.GamePlayer;
import fr.joupi.api.utils.concurrent.BukkitThreading;
import fr.joupi.api.utils.task.countdown.CountdownTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;

@Getter
@Setter
public abstract class GameWaitingRoom<M extends MiniGame, G extends GamePlayer<?>, I extends Enum<I> & WaitingRoomItems> implements WaitingRoom {

    protected final M game;
    protected final I[] items;

    protected CountdownTask countdownTask;
    protected Map map;

    public GameWaitingRoom(M game, CountdownTask countdownTask, Map map, Class<I> itemsClass) {
        this.game = game;
        this.countdownTask = countdownTask;
        this.map = map;
        this.items = itemsClass.getEnumConstants();
    }

    protected abstract void onJoin(G gamePlayer);
    protected abstract void onLeave(G gamePlayer);

    @Override
    public void processJoin(GamePlayer<?> gamePlayer) {
        var spawn = map.getSpawn();
        cleanUpPlayer(gamePlayer);

        if (map != null && spawn != null)
            BukkitThreading.runTask(() -> gamePlayer.teleport(spawn));

        giveItems(gamePlayer);
        tryToStartTimer();
        onJoin((G) gamePlayer);
    }

    @Override
    public void processLeave(GamePlayer<?> gamePlayer) {
        onLeave((G) gamePlayer);
        tryToCancelTimer();
    }

    @Override
    public void giveItems(GamePlayer<?> gamePlayer) {
        for (I item : items)
            gamePlayer.getInventory().setItem(item.getSlot(), item.getItemStack());
    }

    @Override
    public void teleport(GamePlayer<?> gamePlayer) {
        var spawn = map.getSpawn();

        if (spawn != null)
            BukkitThreading.runTask(() -> gamePlayer.teleport(spawn));
    }

    @Override
    public void tryToStartTimer() {
        if (countdownTask != null && game.canStart())
            countdownTask.run();
    }

    @Override
    public void tryToCancelTimer() {
        if (countdownTask != null && !game.canStart() && countdownTask.isStarted())
            countdownTask.cancel();
    }

    private void cleanUpPlayer(GamePlayer<?> gamePlayer) {
        var player = gamePlayer.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().clear();

        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0f);
    }

}