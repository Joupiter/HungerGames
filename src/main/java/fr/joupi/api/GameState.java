package fr.joupi.api;

import lombok.Getter;

@Getter
public enum GameState {

    LOADING,
    WAIT,
    STARTING,
    IN_GAME,
    END;

    public boolean is(GameState state) {
        return this == state;
    }

    public boolean is(GameState... states) {
        for (GameState state : states)
            if (this == state) return true;

        return false;
    }

}