package fr.joupi.hg.settings;

import java.util.ArrayList;
import java.util.List;

public enum HungerMessages {

    PREFIX ("§6§lHUNGERGAMES §8┃ "),

    PLAYER_SPECTATE_MESSAGE ("§e%s §7a rejoint en tant que spectateur."),
    PLAYER_JOIN_MESSAGE ("§a+ §e%s §7(%d/%d)"),
    PLAYER_LEAVE_MESSAGE ("§c- §e%s §7(%d/%d)"),

    STARTING_CANCELLED ("§cLancement de la partie annuler..."),
    STARTING_MESSAGE ("§fLancement dans §e%d §fsecondes !"),

    KILL_MESSAGE ("§c%s §ea été tué par §c%s §e!"),

    END_VICTORY_MESSAGE (
            "§m-------------------------------------------",
            "",
            "§f§l➨ CLASSEMENT",
            "",
            "%s",
            "",
            "§a① §f%s",
            "§e② §f%s",
            "§c③ §f%s",
            "",
            "§fTu vas être redirigé vers le hub dans quelques secondes...",
            "",
            "§m-------------------------------------------"),

    END_BACK_TO_HUB ("§fRetour au hub dans §e%d §fsecondes !");

    private String message;
    private String[] messages;

    HungerMessages(String message) {
        this.message = message;
    }

    HungerMessages(String... messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }

    public String getMessage(Object... objects) {
        return PREFIX.message + message.formatted(objects);
    }

    public String[] getMessages(Object... objects) {
        List<String> formattedMessages = new ArrayList<>(messages.length);
        var index = 0;

        for (String message : messages) {
            if (message.contains("%")) {
                formattedMessages.add(message.formatted(objects[index]));
                index++;
            } else
                formattedMessages.add(message);
        }

        return formattedMessages.toArray(String[]::new);
    }

    public String getMessageWithoutPrefix() {
        return message;
    }

}