package fr.joupi.hg.settings;

import fr.joupi.api.GameSettings;
import fr.joupi.api.GameSize;
import fr.joupi.api.map.GameMap;
import fr.joupi.api.map.Map;
import fr.joupi.api.utils.EnumDirection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HungerSettings extends GameSettings {

    private final Map waitingMap, arenaMap;

    private int startTimer, gameLifeTime;

    public HungerSettings(GameSize gameSize) {
        super(gameSize);
        this.waitingMap = new GameMap("waiting"/*, "lobby", 0, 80, 0, EnumDirection.NORTH*/);
        this.arenaMap = new GameMap("arena", "hgmap", 0, 80, 0, EnumDirection.NORTH);
        this.startTimer = 20; // JUST AN RANDOM VALUE
        this.gameLifeTime = 360; // JUST AN RANDOM VALUE
    }

}