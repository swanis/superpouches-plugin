package me.swanis.pouches.commands.subcommands;

import me.swanis.pouches.Pouches;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class PouchesListCommand extends PluginCommand {

    private Pouches instance;

    public PouchesListCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "list", permission = "pouches.list", subCommand = true, baseCommand = "pouches")
    public void onCommand(CommandSender commandSender, String[] args) {
        Set<String> keySet = instance.getPouchManager().getPouches().keySet();
        String[] pouches = keySet.toArray(new String[keySet.size()]);
        String list = String.join(", ", pouches);

        commandSender.sendMessage(list);
    }
}
