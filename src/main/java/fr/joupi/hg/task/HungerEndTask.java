package fr.joupi.hg.task;

import fr.joupi.api.GameState;
import fr.joupi.api.utils.task.countdown.CountdownTask;
import fr.joupi.api.utils.task.countdown.GameCountdownTask;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;
import fr.joupi.hg.settings.HungerMessages;
import org.bukkit.Bukkit;

public class HungerEndTask extends GameCountdownTask<HungerGame> {

    public HungerEndTask(HungerGame game) {
        super(10, game);
    }

    @Override
    public void onStart() {
        game.setState(GameState.END);

        game.getPlayers().values().forEach(HungerPlayer::cleanUp);
        game.getFirstTeamAlive().ifPresent(game::onWin);
        // GIVE END ITEM (back to hub ect)
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
        Bukkit.shutdown();
    }

    @Override
    public void onCancel() {
        // SHOULD NOT BE CALLED
    }

}