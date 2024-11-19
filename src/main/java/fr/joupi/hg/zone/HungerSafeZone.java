package fr.joupi.hg.zone;

import fr.joupi.api.zone.CuboidZone;
import fr.joupi.api.zone.GameCuboidZone;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;

public class HungerSafeZone extends GameCuboidZone<HungerGame> {

    public HungerSafeZone(HungerGame game) {
        super(game, game.getSettings().getSafeZoneCenter(), game.getSettings().getSafeZoneSize());
    }

    @Override
    public void onEnter(HungerPlayer hungerPlayer) {
        hungerPlayer.sendMessage("You are in safe zone now !");
    }

    @Override
    public void onExit(HungerPlayer hungerPlayer) {
        hungerPlayer.sendMessage("You exit the safe zone be careful !");
    }

    @Override
    public void onStart() {
        game.broadcast("Safe zone start to move !");
    }

    @Override
    public void onNext(CuboidZone zone) {
        game.broadcast("Safe zone move TOP_CORNER:" + zone.getTopCorner().toString() + " | BOT_CORNER:" + zone.getBotCorner());
    }

    @Override
    public void onEnd() {
        game.broadcast("Safe zone bye bye");
    }

}