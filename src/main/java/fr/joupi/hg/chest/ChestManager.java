package fr.joupi.hg.chest;

import fr.joupi.api.MiniGame;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class ChestManager<M extends MiniGame, I extends Enum<I> & ChestItems> {

    protected M game;
    protected I[] items;

    protected ChestFillTask fillTask;

    public ChestManager(M game, Class<I> itemsClass) {
        this.game = game;
        this.items = itemsClass.getEnumConstants();
    }

    public void refillChest() {
        fillTask.run(getItemStacks());
    }

    public List<ItemStack> getItemStacks() {
        return Arrays.stream(items).map(ChestItems::getItemStack).toList();
    }

}