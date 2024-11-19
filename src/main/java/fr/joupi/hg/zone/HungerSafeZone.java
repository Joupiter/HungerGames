package fr.joupi.hg.zone;

import fr.joupi.api.zone.CuboidZone;
import fr.joupi.api.zone.GameCuboidZone;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;

public class HungerSafeZone extends GameCuboidZone<HungerGame, HungerPlayer> {

    public HungerSafeZone(HungerGame game) {
        super(game, game.getSettings().getSafeZoneCenter(), game.getSettings().getSafeZoneSize());
    }

    @Override
    public void onEnter(HungerPlayer hungerPlayer) {
        hungerPlayer.sendMessage("§aYou are in safe zone now !");
    }

    @Override
    public void onExit(HungerPlayer hungerPlayer) {
        hungerPlayer.sendMessage("§cYou exit the safe zone be careful !");
    }

    @Override
    public void onStart() {
        game.broadcast("§eSafe zone start to move !");
    }

    @Override
    public void onNext(CuboidZone zone) {
        // DEBOGUAGE POUR DEBOGUER LES BOGUES
        //game.broadcast("Safe zone move TOP_CORNER:" + zone.getTopCorner().toString() + " | BOT_CORNER:" + zone.getBotCorner());
    }

    @Override
    public void onEnd() {
        game.broadcast("§5Safe zone bye bye");
    }

}