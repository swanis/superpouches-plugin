package me.swanis.pouches.commands.subcommands;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.pouch.Pouch;
import me.swanis.pouches.utils.ItemBuilder;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PouchesGiveCommand extends PluginCommand {

    private Pouches instance;

    public PouchesGiveCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "give", permission = "pouches.give", subCommand = true, baseCommand = "pouches")
    public void onCommand(CommandSender commandSender, String[] args) {
        if(args.length < 4) {
            commandSender.sendMessage(Configuration.USAGE_MESSAGE.replace("%usage%", "/pouches give <player> <type> <amount>"));
            return;
        }

        if(instance.getServer().getPlayerExact(args[1]) == null) {
            commandSender.sendMessage(Configuration.PLAYER_NOT_FOUND_MESSAGE.replace("%player%", args[1]));
            return;
        }

        Player target = instance.getServer().getPlayerExact(args[1]);
        Pouch pouch = instance.getPouchManager().getPouch(args[2]);

        if(pouch == null) {
            commandSender.sendMessage(Configuration.NO_POUCH_CALLED_THAT_MESSAGE.replace("%name%", args[2]));
            return;
        }


        if(!StringUtils.isNumeric(args[3])) {
            commandSender.sendMessage(Configuration.NOT_NUMERIC_MESSAGE.replace("%arg%", args[2]));
            return;
        }

        int amount = Integer.valueOf(args[3]);

        if(amount < 1) {
            commandSender.sendMessage(Configuration.AMOUNT_CANNOT_BE_ZERO_MESSAGE);
            return;
        }


        int subtract = 0;
        int amountAdded = 0;

        for (int i = 0; i < 35; i++) {
            ItemStack item = target.getInventory().getItem(i);

            if (item == null) {
                int amountToAdd = amount - amountAdded > 64 ? 64 : amount - subtract;

                if (amountToAdd != 0) {
                    ItemStack itemToAdd = new ItemBuilder(pouch.getItem())
                            .setAmount(amountToAdd)
                            .toItemStack();

                    target.getInventory().setItem(i, itemToAdd);
                }

                amountAdded += amountToAdd;

                if (amountToAdd != 64) break;

                subtract += 64;
            } else if (item.isSimilar(pouch.getItem())) {
                int amountToAdd = amount - amountAdded > 64 - item.getAmount() ? 64 - item.getAmount() : amount - subtract;

                if (amountToAdd != 0) {
                    ItemStack itemToAdd = new ItemBuilder(pouch.getItem())
                            .setAmount(item.getAmount() + amountToAdd)
                            .toItemStack();

                    target.getInventory().setItem(i, itemToAdd);
                }

                amountAdded += amountToAdd;

                if (amountToAdd != 64 - item.getAmount()) break;

                subtract += 64 - item.getAmount();
            }
        }

        if (amountAdded < amount) {
            int toDrop = amount - amountAdded;

            for (int i = 0; i < toDrop; i++) target.getWorld().dropItem(target.getLocation(), new ItemBuilder(pouch.getItem()).setAmount(1).toItemStack());
        }

        commandSender.sendMessage(Configuration.GAVE_POUCHES_TO_MESSAGE.replace("%amount%", String.valueOf(amount)).replace("%type%", pouch.getName()).replace("%target%", target.getName()));
        target.sendMessage(Configuration.RECEIVED_POUCHES_FROM_MESSAGE.replace("%amount%", String.valueOf(amount)).replace("%type%", pouch.getName()).replace("%sender%", commandSender.getName()));
    }
}
