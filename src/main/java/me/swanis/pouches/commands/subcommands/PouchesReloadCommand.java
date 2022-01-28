package me.swanis.pouches.commands.subcommands;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.bukkit.command.CommandSender;

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
