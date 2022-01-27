package me.swanis.pouches.commands.subcommands;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.bukkit.command.CommandSender;

public class PouchesHelpCommand extends PluginCommand {

    private Pouches instance;

    public PouchesHelpCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "help", permission = "pouches.help", subCommand = true, baseCommand = "pouches")
    public void onCommand(CommandSender commandSender, String[] args) {
        Configuration.POUCHES_HELP_MESSAGE.forEach(commandSender::sendMessage);
    }
}
