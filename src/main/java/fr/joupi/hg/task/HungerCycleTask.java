package fr.joupi.hg.task;

import fr.joupi.api.utils.task.cycle.CycleTask;
import fr.joupi.api.utils.task.cycle.GameCycleTask;
import fr.joupi.hg.HungerGame;

public class HungerCycleTask extends GameCycleTask<HungerGame> {

    public HungerCycleTask(HungerGame game) {
        super(game.getSettings().getGameLifeTime(), game);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onNext(CycleTask task) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onCancel() {

    }

}