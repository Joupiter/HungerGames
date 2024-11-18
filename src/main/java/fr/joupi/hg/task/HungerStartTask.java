package fr.joupi.hg.task;

import fr.joupi.api.GameState;
import fr.joupi.api.utils.task.countdown.CountdownTask;
import fr.joupi.api.utils.task.countdown.GameCountdownTask;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.settings.HungerMessages;
import org.bukkit.Sound;

public class HungerStartTask extends GameCountdownTask<HungerGame> {

    public HungerStartTask(HungerGame game) {
        super(game.getSettings().getStartTimer(), game);
    }

    @Override
    public void onStart() {
        game.setState(GameState.STARTING);
    }

    @Override
    public void onNext(CountdownTask task) {
        game.broadcast(HungerMessages.STARTING_MESSAGE.getMessage(secondsLeft.get()));

        game.getAlivePlayers()
                .forEach(gamePlayer -> {
                    gamePlayer.getPlayer().setLevel(secondsLeft.get());
                    gamePlayer.getPlayer().playSound(gamePlayer.getLocation(), Sound.NOTE_PLING, 20f, 20f);

                    switch (secondsLeft.get()) {
                        case 3 -> gamePlayer.sendTitle(20, 20, 20, "§c3", "");
                        case 2 -> gamePlayer.sendTitle(20, 20, 20, "§62", "");
                        case 1 -> gamePlayer.sendTitle(20, 20, 20, "§e1", "");
                    }
                });
    }

    @Override
    public void onComplete() {
        game.fillTeam();
    }

    @Override
    public void onCancel() {
        game.setState(GameState.WAIT);
        game.broadcast(HungerMessages.STARTING_CANCELLED.getMessage());
    }

}