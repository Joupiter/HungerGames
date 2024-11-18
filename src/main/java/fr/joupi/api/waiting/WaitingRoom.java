package fr.joupi.api.waiting;

import fr.joupi.api.map.Map;
import fr.joupi.api.player.GamePlayer;
import fr.joupi.api.utils.task.countdown.CountdownTask;

public interface WaitingRoom {

    void processJoin(GamePlayer<?> gamePlayer);
    void processLeave(GamePlayer<?> gamePlayer);

    CountdownTask getCountdownTask();
    Map getMap();

    void giveItems(GamePlayer<?> gamePlayer);
    void teleport(GamePlayer<?> gamePlayer);

    void tryToStartTimer();
    void tryToCancelTimer();

}
