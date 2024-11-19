package fr.joupi.hg.chest;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface ChestFillTask {

    void run(Collection<ItemStack> itemStacks);

}