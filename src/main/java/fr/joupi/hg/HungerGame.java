package fr.joupi.hg;

import fr.joupi.api.Game;
import fr.joupi.api.GameState;
import fr.joupi.api.team.GameTeamColor;
import fr.joupi.api.utils.GameSizeTemplate;
import fr.joupi.api.utils.task.countdown.CountdownTask;
import fr.joupi.api.utils.task.cycle.CycleTask;
import fr.joupi.hg.player.HungerPlayer;
import fr.joupi.hg.player.HungerTeam;
import fr.joupi.hg.settings.HungerSettings;
import fr.joupi.hg.task.HungerCycleTask;
import fr.joupi.hg.task.HungerEndTask;
import fr.joupi.hg.waiting.HungerWaitingRoom;
import fr.joupi.hg.zone.HungerSafeZone;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
public class HungerGame extends Game<HungerPlayer, HungerTeam, HungerSettings> {

    private final CycleTask cycleTask;
    private final CountdownTask endTask;

    private final HungerSafeZone safeZone;


    /**
     * OR WE CAN ALSO PUT IN CONSTRUCTOR GAME SIZE FOR CHOOSE THE SIZE OF THE GAME.
     * {@link fr.joupi.api.utils.GameSizeTemplate}<br>
     * <br>
     * SAME FOR CHOOSE THE MAP OR OTHERS SETTINGS
     */
    public HungerGame() {
        super("HungerGames", new HungerSettings(GameSizeTemplate.SIZE_SOLO.toGameSize()));
        this.waitingRoom = new HungerWaitingRoom(this);
        this.safeZone = new HungerSafeZone(this);
        this.cycleTask = new HungerCycleTask(this, safeZone);
        this.endTask = new HungerEndTask(this);
        this.load();
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

    public List<HungerPlayer> getTopKillers() {
        return getAlivePlayers().stream()
                .sorted(Comparator.comparingInt(HungerPlayer::getKills).reversed())
                .toList();
    }

    public String getTopKiller(int position) {
        var killers = getTopKillers();

        if (position < 0 || position >= killers.size()) {
            return "personne";
        }

        var killer = killers.get(position);

        return killer.getName() + " §8(§b" + killer.getKills() + "§8)";
    }

    public String getTopKiller(HungerPlayer gamePlayer) {
        var killers = getTopKillers();
        var index = killers.indexOf(gamePlayer);

        return "#" + index;
    }

}