package me.swanis.pouches.commands;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.utils.command.Command;
import me.swanis.pouches.utils.command.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PouchesCommand extends PluginCommand {

    private Pouches instance;

    public PouchesCommand(Pouches instance) {
        super(instance);
        this.instance = instance;
    }

    @Command(command = "pouches", permission = "pouches.use", subCommands = {"help", "give", "list", "author", "reload"})
    public void onCommand(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(Configuration.NOT_PLAYER_MESSAGE);
            return;
        }

        Player player = (Player) commandSender;

        player.openInventory(instance.getPouchManager().getPages().get(0));
    }
}
