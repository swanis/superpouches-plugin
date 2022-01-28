package me.swanis.pouches.commands.subcommands;

import me.swanis.pouches.Pouches;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.bukkit.command.CommandSender;

public class PouchesAuthorCommand extends PluginCommand {

    private Pouches instance;

    public PouchesAuthorCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "author", subCommand = true, baseCommand = "pouches")
    public void onCommand(CommandSender commandSender, String[] args) {
        commandSender.sendMessage("This server is running SuperPouches v1.2.6 created by Swanis (https://www.mc-market.org/members/71127/)");
    }
}
