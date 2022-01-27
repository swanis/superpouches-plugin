package me.swanis.pouches;

import me.swanis.pouches.commands.PouchesCommand;
import me.swanis.pouches.commands.subcommands.*;
import me.swanis.pouches.listeners.InventoryListener;
import me.swanis.pouches.listeners.PlayerListener;
import me.swanis.pouches.pouch.Pouch;
import me.swanis.pouches.pouch.PouchManager;
import me.swanis.pouches.utils.ItemBuilder;
import me.swanis.pouches.utils.MetricsLite;
import me.swanis.pouches.utils.command.CommandManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Pouches extends JavaPlugin {

    private PouchManager pouchManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        loadConfiguration();

        registerManagers();
        registerCommands();
        registerListeners();

        new MetricsLite(this);
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(this::refundOpener);
    }

    private void loadConfiguration() {
        saveDefaultConfig();
        new Configuration(this);
    }

    private void registerManagers() {
        this.pouchManager = new PouchManager(this);
        this.commandManager = new CommandManager(this);
    }

    private void registerCommands() {
        commandManager.register(new PouchesCommand(this));
        commandManager.register(new PouchesHelpCommand(this));
        commandManager.register(new PouchesGiveCommand(this));
        commandManager.register(new PouchesListCommand(this));
        commandManager.register(new PouchesAuthorCommand(this));
        commandManager.register(new PouchesReloadCommand(this));
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(this), this);
        pluginManager.registerEvents(new InventoryListener(this), this);
    }

    private void refundOpener(Player player) {
        Pouch pouch = pouchManager.getOpeners().get(player.getUniqueId());

        if(pouch == null) return;

        ItemStack pouchItem = new ItemBuilder(pouch.getItem())
                .setAmount(1)
                .toItemStack();

        player.getInventory().addItem(pouchItem);

        pouchManager.getOpeners().remove(player.getUniqueId());
    }

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
