package fr.joupi.api;

import fr.joupi.api.player.GamePlayer;
import fr.joupi.api.team.GameTeam;

public interface GameObserver<G extends GamePlayer<T>, T extends GameTeam<G>> {

    void onWin(T gameTeam);

}