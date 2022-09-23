package me.swanis.pouches.pouch;

import me.swanis.pouches.Configuration;
import me.swanis.pouches.Pouches;
import me.swanis.pouches.pouch.reward.Reward;
import me.swanis.pouches.utils.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;

public class PouchManager {

    private Pouches instance;

    private Map<String, Pouch> pouches = new LinkedHashMap<>();
    private YamlFile numberPouchesFile;
    private YamlFile itemPouchesFile;
    private List<Inventory> pages = new ArrayList<>();
    private Map<UUID, Pouch> openers = new HashMap<>();

    public PouchManager(Pouches instance) {
        this.instance = instance;

        numberPouchesFile = new YamlFile("numberpouches", instance);
        itemPouchesFile = new YamlFile("itempouches", instance);

        loadNumberPouches();
        loadItemPouches();
        loadInventory();
    }

    public void load(Pouch pouch) {
        pouches.put(pouch.getConfigKey().toLowerCase(), pouch);
    }

    public void unload(Pouch pouch) {
        pouches.remove(pouch.getConfigKey());
    }

    public Pouch getPouch(String string) {
        return pouches.get(string.toLowerCase());
    }

    public Pouch getPouch(ItemStack itemStack) {
        return pouches.values().stream().filter(pouch -> pouch.getItem().isSimilar(itemStack)).findFirst().orElse(null);
    }

    public Inventory getPage(int i) {
        return i >= 0 && pages.size() > i ? pages.get(i) : null;
    }

    public int getPage(Inventory inventory) {
        for (int i = 0; i < pages.size(); i++) {
            if(InventoryUtil.isSimilarInventory(inventory, pages.get(i))) {
                return i;
            }
        }

        return -1;
    }

    public void reloadPouches() {
        pouches.clear();
        numberPouchesFile = new YamlFile("numberpouches", instance);
        itemPouchesFile = new YamlFile("itempouches", instance);
        loadNumberPouches();
        loadItemPouches();
    }

    public void reloadInventory() {
        pages.clear();
        loadInventory();
    }

    public Map<String, Pouch> getPouches() {
        return pouches;
    }

    public List<Inventory> getPages() {
        return pages;
    }

    public Map<UUID, Pouch> getOpeners() {
        return openers;
    }

    private void loadNumberPouches() {
        FileConfiguration config = numberPouchesFile.getConfig();

        config.getKeys(false).forEach(string -> {
            String name = config.getString(string + ".name");
            String message = StringUtil.color(config.getString(string + ".message"));
            boolean titleEnabled = config.getBoolean(string + ".title.enabled");
            String titleMessage = StringUtil.color(config.getString(string + ".title.message"));
            String titleSubMessage = StringUtil.color(config.getString(string + ".title.submessage"));
            boolean titleRight = config.getBoolean(string + ".title.reveal_from_right");
            String titleColor = StringUtil.color(config.getString(string + ".title.color_after_randomization"));
            int titleFadeIn = config.getInt(string + ".title.fadein_ticks");
            int titleStay = config.getInt(string + ".title.stay_ticks");
            int titleFadeOut = config.getInt(string + ".title.fadeout_ticks");
            int titleUpdate = config.getInt(string + ".title.update_ticks");
            boolean soundEnabled = config.getBoolean(string + ".sound.enabled");
            Sound soundType;

            try {
                soundType = Sounds.valueOf(config.getString(string + ".sound.type").toUpperCase()).bukkitSound();
            } catch (IllegalArgumentException e) {
                soundType = Sound.valueOf(config.getString(string + ".sound.type").toUpperCase());
            }

            int soundVolume = config.getInt(string + ".sound.volume");
            int soundPitch = config.getInt(string + ".sound.pitch");
            List<String> commands = config.getStringList(string + ".commands");
            long min = config.getLong(string + ".min");
            long max = config.getLong(string + ".max");
            boolean formatEnabled = config.getBoolean(string + ".format.enabled");
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag(config.getString(string + ".format.locale")));

            Material itemMaterial = CompatibilityUtil.getMaterial(config.getString(string + ".item.material").toUpperCase());
            String itemName = StringUtil.color(config.getString(string + ".item.name"));
            List<String> itemLore = config.getStringList(string + ".item.lore");
            List<String> lore = new ArrayList<>();

            itemLore.forEach(string1 -> lore.add(StringUtil.color(string1.replace("%min%", String.valueOf(min)).replace("%max%", String.valueOf(max)))));

            short durability = (short) config.getInt(string + ".item.durability");
            boolean glow = config.getBoolean(string + ".item.glow");

            ItemStack item = new ItemBuilder(itemMaterial)
                    .setName(itemName)
                    .setLore(lore)
                    .setDurability(durability)
                    .addGlow(glow)
                    .toItemStack();

            Pouch pouch = new Pouch(string, true);

            pouch.setName(name);
            pouch.setMessage(message);
            pouch.setTitleEnabled(titleEnabled);
            pouch.setTitleMessage(titleMessage);
            pouch.setTitleSubMessage(titleSubMessage);
            pouch.setTitleRight(titleRight);
            pouch.setTitleColor(titleColor);
            pouch.setTitleFadeIn(titleFadeIn);
            pouch.setTitleStay(titleStay);
            pouch.setTitleFadeOut(titleFadeOut);
            pouch.setTitleUpdate(titleUpdate);
            pouch.setSoundEnabled(soundEnabled);
            pouch.setSoundType(soundType);
            pouch.setSoundVolume(soundVolume);
            pouch.setSoundPitch(soundPitch);
            pouch.setCommands(commands);
            pouch.setMin(min);
            pouch.setMax(max);
            pouch.setFormatEnabled(formatEnabled);
            pouch.setNumberFormat(numberFormat);
            pouch.setItem(item);

            load(pouch);
        });
    }

    private void loadItemPouches() {
        FileConfiguration config = itemPouchesFile.getConfig();

        config.getKeys(false).forEach(string -> {
            String name = config.getString(string + ".name");
            String message = StringUtil.color(config.getString(string + ".message"));
            boolean titleEnabled = config.getBoolean(string + ".title.enabled");
            String titleMessage = StringUtil.color(config.getString(string + ".title.message"));
            String titleSubMessage = StringUtil.color(config.getString(string + ".title.submessage"));
            boolean titleRight = config.getBoolean(string + ".title.reveal_from_right");
            String titleColor = StringUtil.color(config.getString(string + ".title.color_after_randomization"));
            int titleFadeIn = config.getInt(string + ".title.fadein_ticks");
            int titleStay = config.getInt(string + ".title.stay_ticks");
            int titleFadeOut = config.getInt(string + ".title.fadeout_ticks");
            int titleUpdate = config.getInt(string + ".title.update_ticks");
            boolean soundEnabled = config.getBoolean(string + ".sound.enabled");
            Sound soundType;

            try {
                soundType = Sounds.valueOf(config.getString(string + ".sound.type").toUpperCase()).bukkitSound();
            } catch (IllegalArgumentException e) {
                soundType = Sound.valueOf(config.getString(string + ".sound.type").toUpperCase());
            }

            int soundVolume = config.getInt(string + ".sound.volume");
            int soundPitch = config.getInt(string + ".sound.pitch");
            List<Reward> rewards = new ArrayList<>();

            config.getConfigurationSection(string + ".rewards").getKeys(false).forEach(string1 -> {
                String rewardName = config.getString(string + ".rewards." + string1 + ".name");
                int rewardChance = config.getInt(string + ".rewards." + string1 + ".chance");
                List<String> rewardCommands = config.getStringList(string + ".rewards." + string1 + ".commands");

                Reward reward = new Reward(rewardName);

                reward.setChance(rewardChance);
                reward.setCommands(rewardCommands);

                rewards.add(reward);
            });

            Material itemMaterial = CompatibilityUtil.getMaterial(config.getString(string + ".item.material").toUpperCase());
            String itemName = StringUtil.color(config.getString(string + ".item.name"));
            List<String> itemLore = config.getStringList(string + ".item.lore");
            List<String> lore = new ArrayList<>();

            itemLore.forEach(string1 -> lore.add(StringUtil.color(string1.replace("%amount%", String.valueOf(rewards.size())))));

            short durability = (short) config.getInt(string + ".item.durability");
            boolean glow = config.getBoolean(string + ".item.glow");

            ItemStack item = new ItemBuilder(itemMaterial)
                    .setName(itemName)
                    .setLore(lore)
                    .setDurability(durability)
                    .addGlow(glow)
                    .toItemStack();

            Pouch pouch = new Pouch(string, false);

            pouch.setName(name);
            pouch.setMessage(message);
            pouch.setTitleEnabled(titleEnabled);
            pouch.setTitleMessage(titleMessage);
            pouch.setTitleSubMessage(titleSubMessage);
            pouch.setTitleRight(titleRight);
            pouch.setTitleColor(titleColor);
            pouch.setTitleFadeIn(titleFadeIn);
            pouch.setTitleStay(titleStay);
            pouch.setTitleFadeOut(titleFadeOut);
            pouch.setTitleUpdate(titleUpdate);
            pouch.setSoundEnabled(soundEnabled);
            pouch.setSoundType(soundType);
            pouch.setSoundVolume(soundVolume);
            pouch.setSoundPitch(soundPitch);
            pouch.setRewards(rewards);
            pouch.setItem(item);

            load(pouch);
        });
    }

    private void loadInventory() {
        int p = (pouches.size() - 1) / Configuration.GUI_POUCHES_PER_PAGE + 1;

        for (int i = 0; i < p; i++) {
            Inventory inventory = instance.getServer().createInventory(null, (Configuration.GUI_ROWS * 9), Configuration.GUI_TITLE);

            ItemStack nextPage = new ItemBuilder(Configuration.GUI_ITEMS_NEXTPAGE_MATERIAL).setName(Configuration.GUI_ITEMS_NEXTPAGE_NAME).setLore(Configuration.GUI_ITEMS_NEXTPAGE_LORE).setDurability(Configuration.GUI_ITEMS_NEXTPAGE_DURABILITY).toItemStack();
            ItemStack previousPage = new ItemBuilder(Configuration.GUI_ITEMS_PREVIOUSPAGE_MATERIAL).setName(Configuration.GUI_ITEMS_PREVIOUSPAGE_NAME).setLore(Configuration.GUI_ITEMS_PREVIOUSPAGE_LORE).setDurability(Configuration.GUI_ITEMS_PREVIOUSPAGE_DURABILITY).toItemStack();
            ItemStack close = new ItemBuilder(Configuration.GUI_ITEMS_CLOSEGUI_MATERIAL).setName(Configuration.GUI_ITEMS_CLOSEGUI_NAME).setLore(Configuration.GUI_ITEMS_CLOSEGUI_LORE).setDurability(Configuration.GUI_ITEMS_CLOSEGUI_DURABILITY).toItemStack();

            inventory.setItem(Configuration.GUI_ITEMS_NEXTPAGE_SLOT, nextPage);
            inventory.setItem(Configuration.GUI_ITEMS_PREVIOUSPAGE_SLOT, previousPage);
            inventory.setItem(Configuration.GUI_ITEMS_CLOSEGUI_SLOT, close);

            if(Configuration.GUI_FILLER_ENABLED) {
                ItemStack fillerItem = new ItemBuilder(Configuration.GUI_FILLER_ITEM_MATERIAL)
                        .setName(Configuration.GUI_FILLER_ITEM_NAME)
                        .setLore(Configuration.GUI_FILLER_ITEM_LORE)
                        .setDurability(Configuration.GUI_FILLER_ITEM_DURABILITY)
                        .toItemStack();

                for(int j = 0; j < inventory.getSize(); j++) {
                    if(inventory.getItem(j) == null)
                        inventory.setItem(j, fillerItem);
                }
            }

            pages.add(inventory);
        }

        int i = 0;
        int page = 0;

        for(Pouch pouch : pouches.values()) {
            if(i == Configuration.GUI_POUCHES_PER_PAGE) {
                i = 0;
                page++;
            }

            Inventory inventory = pages.get(page);

            inventory.setItem(i, pouch.getItem());

            i++;
        }
    }
}
