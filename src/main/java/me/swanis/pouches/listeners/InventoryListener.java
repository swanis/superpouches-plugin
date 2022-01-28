package me.swanis.pouches.listeners;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.pouch.Pouch;
import me.swanis.pouches.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {

    private Pouches instance;

    public InventoryListener(Pouches instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if(inventory == null) return;
        if(!event.getView().getTitle().equals(Configuration.GUI_TITLE)) return;
        if (inventory.getHolder() instanceof Player && ((Player) inventory.getHolder()).getName().equals(player.getName())) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }

            return;
        }

        event.setCancelled(true);

        if(event.getSlot() == Configuration.GUI_ITEMS_NEXTPAGE_SLOT) {
            int page = instance.getPouchManager().getPage(inventory);

            if(page == -1) return;

            Inventory nextPage = instance.getPouchManager().getPage(page + 1);

            if(nextPage == null) return;

            player.openInventory(nextPage);
            return;
        } else if(event.getSlot() == Configuration.GUI_ITEMS_PREVIOUSPAGE_SLOT) {
            int page = instance.getPouchManager().getPage(inventory);

            if(page == -1) return;

            Inventory previousPage = instance.getPouchManager().getPage(page - 1);

            if(previousPage == null) return;

            player.openInventory(previousPage);
            return;
        } else if(event.getSlot() == Configuration.GUI_ITEMS_CLOSEGUI_SLOT) {
            closeInventory(player);
            return;
        }

        ItemStack item = inventory.getItem(event.getSlot());

        if(item == null) return;

        Pouch pouch = instance.getPouchManager().getPouch(item);

        if(pouch == null) return;
        if(!player.hasPermission("pouches.admin")) return;

        if(Configuration.GUI_GIVE_POUCH_BY_CLICKING_IF_PERMISSION) {
            ItemStack pouchItem = new ItemBuilder(pouch.getItem())
                    .setAmount(1)
                    .toItemStack();

            player.getInventory().addItem(pouchItem);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();

        if(inventory == null) return;
        if(!event.getView().getTitle().equals(Configuration.GUI_TITLE)) return;

        event.setCancelled(true);
    }

    private void closeInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }.runTaskLater(instance, 1L);
    }
}
