package me.swanis.pouches.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static boolean isSimilarInventory(Inventory first, Inventory second) {
        if(first == null && second == null) return true;
        if((first == null && second != null) || (first != null && second == null)) return false;
        if(first.getType() != second.getType()) return false;

        ItemStack[] firstContents = first.getContents();
        ItemStack[] secondContents = second.getContents();

        if(firstContents.length != secondContents.length) return false;

        for(int i = 0; i < firstContents.length; i++) {
            if(firstContents[i] == null && secondContents[i] == null) continue;
            else if(firstContents[i] == null && secondContents[i] != null) return false;
            else if(secondContents[i] == null && firstContents[i] != null) return false;
            else if(!firstContents[i].isSimilar(secondContents[i])) return false;
        }

        return true;
    }
}
