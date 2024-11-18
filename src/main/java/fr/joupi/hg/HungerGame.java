package fr.joupi.hg;

import fr.joupi.api.Game;
import fr.joupi.api.GameSize;
import fr.joupi.api.GameState;
import fr.joupi.api.team.GameTeamColor;
import fr.joupi.api.utils.GameSizeTemplate;
import fr.joupi.hg.player.HungerPlayer;
import fr.joupi.hg.player.HungerTeam;
import fr.joupi.hg.settings.HungerSettings;
import fr.joupi.hg.waiting.HungerWaitingRoom;

import java.util.UUID;

public class HungerGame extends Game<HungerPlayer, HungerTeam, HungerSettings> {

    /**
     * OR WE CAN ALSO PUT IN CONSTRUCTOR GAME SIZE FOR CHOOSE THE SIZE OF THE GAME.
     * {@link fr.joupi.api.utils.GameSizeTemplate}<br>
     * <br>
     * SAME FOR CHOOSE THE MAP OR OTHERS SETTINGS
     */
    public HungerGame() {
        super("HungerGames", new HungerSettings(GameSizeTemplate.SIZE_SOLO.toGameSize()));
        this.waitingRoom = new HungerWaitingRoom(this);
    }

    @Override
    public HungerPlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new HungerPlayer(uuid, spectator);
    }

    @Override
    public HungerTeam defaultGameTeam(int maxSize, GameTeamColor teamColor) {
        return new HungerTeam(maxSize, teamColor);
    }

    @Override
    public void load() {
        this.state = GameState.WAIT;
    }

}