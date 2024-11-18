package fr.joupi.hg.player;

import fr.joupi.api.team.GameTeam;
import fr.joupi.api.team.GameTeamColor;

public class HungerTeam extends GameTeam<HungerPlayer> {

    public HungerTeam(int maxSize, GameTeamColor color) {
        super(maxSize, color);
    }

}