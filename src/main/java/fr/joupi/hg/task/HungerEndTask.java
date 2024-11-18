package fr.joupi.hg.task;

import fr.joupi.api.utils.task.countdown.CountdownTask;
import fr.joupi.api.utils.task.countdown.GameCountdownTask;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.settings.HungerMessages;

public class HungerEndTask extends GameCountdownTask<HungerGame> {

    public HungerEndTask(HungerGame game) {
        super(10, game);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onNext(CountdownTask task) {
        var seconds = task.getSecondsLeft().get();

        switch (seconds) {
            case 10, 5 -> game.broadcast(HungerMessages.END_BACK_TO_HUB.getMessage(seconds));
        }
    }

    @Override
    public void onComplete() {
        // STOP SERVER
    }

    @Override
    public void onCancel() {
        // SHOULD NOT BE CALLED
    }

}