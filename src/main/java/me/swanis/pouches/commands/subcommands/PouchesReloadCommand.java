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

public class PouchesReloadCommand extends PluginCommand {

    private Pouches instance;

    public PouchesReloadCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "reload", permission = "pouches.reload", subCommand = true, baseCommand = "pouches")
    public void onCommand(CommandSender commandSender, String[] args) {
        instance.reloadConfig();
        new Configuration(instance);
        instance.getPouchManager().reloadPouches();
        instance.getPouchManager().reloadInventory();
        commandSender.sendMessage("The configuration has been reloaded");
    }
}
