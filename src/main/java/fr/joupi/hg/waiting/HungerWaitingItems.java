package fr.joupi.hg.waiting;

import fr.joupi.api.utils.ItemBuilder;
import fr.joupi.api.waiting.WaitingRoomItems;
import fr.joupi.hg.HungerGame;
import fr.joupi.hg.player.HungerPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
public enum HungerWaitingItems implements WaitingRoomItems {

    TEAM_SELECTOR (
            4,
            new ItemBuilder(Material.NETHER_STAR).setName("§eÉquipes§8・§7Clic droit").build(),
            (game, gamePlayer) -> {
                game.addPlayerToRandomTeam(gamePlayer); // DEBUG THING NEED TO BE DELETED
                gamePlayer.sendMessage("need to be implemented");
            });

    private final int slot;
    private final ItemStack itemStack;
    private final BiConsumer<HungerGame, HungerPlayer> consumer;

    private static final List<HungerWaitingItems> items = Arrays.asList(values());

    public static Optional<HungerWaitingItems> getWaitingItem(ItemStack itemStack) {
        return items.stream().filter(waitingItems -> waitingItems.getItemStack().equals(itemStack)).findFirst();
    }

}