package fr.joupi.hg.waiting;

import fr.joupi.api.waiting.GameWaitingRoom;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;
import fr.joupi.hg.settings.HungerMessages;
import fr.joupi.hg.task.HungerStartTask;

public class HungerWaitingRoom extends GameWaitingRoom<HungerGame, HungerPlayer, HungerWaitingItems> {

    public HungerWaitingRoom(HungerGame game) {
        super(game, new HungerStartTask(game), game.getSettings().getWaitingMap(), HungerWaitingItems.class);
    }

    @Override
    protected void onJoin(HungerPlayer gamePlayer) {
        game.broadcast(HungerMessages.PLAYER_JOIN_MESSAGE.getMessage(gamePlayer.getName(), game.getSize(), game.getGameSize().getMaxPlayer()));
    }

    @Override
    protected void onLeave(HungerPlayer gamePlayer) {
        game.broadcast(HungerMessages.PLAYER_LEAVE_MESSAGE.getMessage(gamePlayer.getName(), game.getSize(), game.getGameSize().getMaxPlayer()));
    }

}