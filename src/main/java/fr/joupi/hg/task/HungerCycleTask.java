package fr.joupi.hg.task;

import fr.joupi.api.GameState;
import fr.joupi.api.utils.concurrent.MultiThreading;
import fr.joupi.api.utils.task.cycle.CycleTask;
import fr.joupi.api.utils.task.cycle.GameCycleTask;
import fr.joupi.hg.HungerGame;
import fr.joupi.api.zone.CuboidZone;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HungerCycleTask extends GameCycleTask<HungerGame> {

    private final CuboidZone zone;
    private ScheduledFuture<?> future;

    public HungerCycleTask(HungerGame game, CuboidZone zone) {
        super(game.getSettings().getSafeZoneReduceTime(), game);
        this.zone = zone;
    }

    @Override
    public void onStart() {
        game.setState(GameState.IN_GAME);
        zone.onStart();
        future = MultiThreading.schedule(() -> game.getPlayers().values().forEach(zone::showBorder), 50, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onNext(CycleTask task) {
        zone.onNext(zone);

        if (zone.isActive())
            zone.reduce(game.getSettings().getSafeZoneReduceBlock());
        else
            end();
    }

    @Override
    public void onComplete() {
        zone.onEnd();
        future.cancel(false);
        game.getEndTask().run();
    }

    @Override
    public void onCancel() {
        // SHOULD NOT BE CALLED
    }

}