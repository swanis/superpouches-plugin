package me.swanis.pouches.listeners;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.pouch.Pouch;
import me.swanis.pouches.pouch.reward.Reward;
import me.swanis.pouches.utils.ItemBuilder;
import me.swanis.pouches.utils.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {

    private Pouches instance;

    public PlayerListener(Pouches instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(event.getItem() == null) return;
        if(!event.getItem().hasItemMeta()) return;
        if(!event.getItem().getItemMeta().hasDisplayName()) return;
        if(!event.getItem().getItemMeta().hasLore()) return;

        Pouch pouch = instance.getPouchManager().getPouch(event.getItem());

        if(pouch == null) return;
        if(!player.getItemInHand().isSimilar(pouch.getItem())) return;

        event.setCancelled(true);

        updateInventory(player);

        if(instance.getPouchManager().getOpeners().containsKey(player.getUniqueId())) {
            player.sendMessage(Configuration.ALREADY_OPENING_POUCH_MESSAGE);
            return;
        }

        if(pouch.isNumber()) {
            instance.getPouchManager().getOpeners().put(player.getUniqueId(), pouch);

            if(event.getItem().getAmount() > 1) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            //int number = random.nextInt(pouch.getMax() - pouch.getMin()) + pouch.getMin() + 1;
            long number = ThreadLocalRandom.current().nextLong(pouch.getMin(), pouch.getMax()) + 1;

            String numberString = pouch.isFormatEnabled() ? pouch.getNumberFormat().format(number) : String.valueOf(number);

            if(!pouch.isTitleEnabled()) {
                pouch.getCommands().forEach(string1 -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string1.replace("%player%", player.getName()).replace("%number%", String.valueOf(number))));
                player.sendMessage(pouch.getMessage().replace("%number%", numberString));
                instance.getPouchManager().getOpeners().remove(player.getUniqueId());
            } else {
                String string = ChatColor.MAGIC.toString() + numberString;

                TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%number%", string), pouch.getTitleSubMessage());

                if(pouch.isTitleRight()) {
                    new BukkitRunnable() {
                        int i = numberString.length() - 1;
                        String string = ChatColor.MAGIC.toString() + numberString + pouch.getTitleColor();

                        @Override
                        public void run() {
                            if(!instance.getPouchManager().getOpeners().containsKey(player.getUniqueId())) cancel();

                            if(i == 0) {
                                pouch.getCommands().forEach(string1 -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string1.replace("%player%", player.getName()).replace("%number%", String.valueOf(number))));
                                player.sendMessage(pouch.getMessage().replace("%number%", numberString));
                                instance.getPouchManager().getOpeners().remove(player.getUniqueId());

                                cancel();
                            }

                            string = string.replaceFirst(Character.toString(ChatColor.stripColor(string).charAt(i)) + pouch.getTitleColor(), pouch.getTitleColor() + Character.toString(numberString.charAt(i)));

                            TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%number%", string), pouch.getTitleSubMessage());

                            if(pouch.isSoundEnabled()) {
                                player.playSound(player.getLocation(), pouch.getSoundType(), pouch.getSoundVolume(), pouch.getSoundPitch());
                            }

                            i--;
                        }
                    }.runTaskTimer(instance, pouch.getTitleUpdate(), pouch.getTitleUpdate());
                } else {
                    new BukkitRunnable() {
                        int i = 0;
                        String string = ChatColor.MAGIC.toString() + numberString;
                        String lastColors = ChatColor.getLastColors(pouch.getTitleMessage().substring(0, pouch.getTitleMessage().indexOf("%number%")));

                        @Override
                        public void run() {
                            if(!instance.getPouchManager().getOpeners().containsKey(player.getUniqueId())) cancel();

                            if(i == numberString.length() - 1) {
                                pouch.getCommands().forEach(string1 -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string1.replace("%player%", player.getName()).replace("%number%", String.valueOf(number))));
                                player.sendMessage(pouch.getMessage().replace("%number%", numberString));
                                instance.getPouchManager().getOpeners().remove(player.getUniqueId());

                                cancel();
                            }

                            string = string.replaceFirst(ChatColor.MAGIC + Character.toString(ChatColor.stripColor(string).charAt(i)), pouch.getTitleColor() + Character.toString(numberString.charAt(i)) + lastColors + ChatColor.MAGIC);

                            TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%number%", string), pouch.getTitleSubMessage());

                            if(pouch.isSoundEnabled()) {
                                player.playSound(player.getLocation(), pouch.getSoundType(), pouch.getSoundVolume(), pouch.getSoundPitch());
                            }

                            i++;
                        }
                    }.runTaskTimer(instance, pouch.getTitleUpdate(), pouch.getTitleUpdate());
                }
            }
        } else {
            int totalChance = 0;

            for(Reward reward : pouch.getRewards()) {
                totalChance += reward.getChance();
            }

            if(totalChance != 100) {
                player.sendMessage("The reward chances for this pouch don't add up to 100 in total");
                return;
            }

            instance.getPouchManager().getOpeners().put(player.getUniqueId(), pouch);

            if(event.getItem().getAmount() > 1) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Reward reward = getRewardByChance(pouch.getRewards());

            if(!pouch.isTitleEnabled()) {
                reward.getCommands().forEach(string -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string.replace("%player%", player.getName())));
                player.sendMessage(pouch.getMessage().replace("%reward%", reward.getName()));
                instance.getPouchManager().getOpeners().remove(player.getUniqueId());
            } else {
                String itemString = ChatColor.MAGIC.toString() + reward.getName();

                TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%item%", itemString), pouch.getTitleSubMessage());

                if(pouch.isTitleRight()) {
                    new BukkitRunnable() {
                        int i = reward.getName().length() - 1;
                        String string = ChatColor.MAGIC.toString() + reward.getName() + pouch.getTitleColor();

                        @Override
                        public void run() {
                            if(!instance.getPouchManager().getOpeners().containsKey(player.getUniqueId())) cancel();

                            if(i == 0) {
                                reward.getCommands().forEach(string -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string.replace("%player%", player.getName())));
                                player.sendMessage(pouch.getMessage().replace("%reward%", reward.getName()));
                                instance.getPouchManager().getOpeners().remove(player.getUniqueId());

                                cancel();
                            }

                            string = string.replaceFirst(Character.toString(ChatColor.stripColor(string).charAt(i)) + pouch.getTitleColor(), pouch.getTitleColor() + Character.toString(reward.getName().charAt(i)));

                            TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%item%", string), pouch.getTitleSubMessage());

                            if(pouch.isSoundEnabled()) {
                                player.playSound(player.getLocation(), pouch.getSoundType(), pouch.getSoundVolume(), pouch.getSoundPitch());
                            }

                            i--;
                        }
                    }.runTaskTimer(instance, pouch.getTitleUpdate(), pouch.getTitleUpdate());
                } else {
                    new BukkitRunnable() {
                        int i = 0;
                        String string = ChatColor.MAGIC.toString() + reward.getName();
                        String lastColors = ChatColor.getLastColors(pouch.getTitleMessage().substring(0, pouch.getTitleMessage().indexOf("%item%")));

                        @Override
                        public void run() {
                            if(!instance.getPouchManager().getOpeners().containsKey(player.getUniqueId())) cancel();

                            if(i == reward.getName().length() - 1) {
                                reward.getCommands().forEach(string -> instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), string.replace("%player%", player.getName())));
                                player.sendMessage(pouch.getMessage().replace("%reward%", reward.getName()));
                                instance.getPouchManager().getOpeners().remove(player.getUniqueId());

                                cancel();
                            }

                            string = string.replaceFirst(ChatColor.MAGIC + Character.toString(ChatColor.stripColor(string).charAt(i)), pouch.getTitleColor() + Character.toString(reward.getName().charAt(i)) + lastColors + ChatColor.MAGIC);

                            TitleUtil.sendTitle(player, pouch.getTitleFadeIn(), pouch.getTitleStay(), pouch.getTitleFadeOut(), pouch.getTitleMessage().replace("%item%", string), pouch.getTitleSubMessage());

                            if(pouch.isSoundEnabled()) {
                                player.playSound(player.getLocation(), pouch.getSoundType(), pouch.getSoundVolume(), pouch.getSoundPitch());
                            }

                            i++;
                        }
                    }.runTaskTimer(instance, pouch.getTitleUpdate(), pouch.getTitleUpdate());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Pouch pouch = instance.getPouchManager().getOpeners().get(player.getUniqueId());

        if(pouch == null) return;

        ItemStack pouchItem = new ItemBuilder(pouch.getItem())
                .setAmount(1)
                .toItemStack();

        player.getInventory().addItem(pouchItem);

        instance.getPouchManager().getOpeners().remove(player.getUniqueId());
    }

    private void updateInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        }.runTaskLater(instance, 1L);
    }

    private Reward getRewardByChance(List<Reward> rewards) {
        Random random = new Random();
        int r = random.nextInt(100);
        int a = 0;

        for(Reward reward : rewards) {
            a += reward.getChance();

            if(r <= (a - 1)) {
                return reward;
            }
        }

        return null;
    }
}
