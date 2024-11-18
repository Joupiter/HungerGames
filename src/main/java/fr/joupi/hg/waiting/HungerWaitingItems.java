package fr.joupi.hg.waiting;

import fr.joupi.api.waiting.WaitingRoomItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public enum HungerWaitingItems implements WaitingRoomItems {

    TEAM_SELECTOR (4, new ItemStack(Material.NETHER_STAR));

    private final int slot;
    private final ItemStack itemStack;

}