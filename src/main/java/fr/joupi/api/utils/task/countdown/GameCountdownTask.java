package fr.joupi.api.utils.task.countdown;

import fr.joupi.api.MiniGame;
import lombok.Getter;

@Getter
public abstract class GameCountdownTask<M extends MiniGame> extends CountdownTask {

    protected final M game;

    public GameCountdownTask(int duration, boolean virtual, M game) {
        super(duration, virtual);
        this.game = game;
    }

    public GameCountdownTask(int duration, M game) {
        this(duration, true, game);
    }

}