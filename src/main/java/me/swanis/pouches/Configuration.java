package me.swanis.pouches;

import me.swanis.pouches.utils.CompatibilityUtil;
import me.swanis.pouches.utils.StringUtil;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    public static String GUI_TITLE;
    public static int GUI_ROWS;
    public static int GUI_POUCHES_PER_PAGE;
    public static boolean GUI_GIVE_POUCH_BY_CLICKING_IF_PERMISSION;
    public static Material GUI_ITEMS_NEXTPAGE_MATERIAL;
    public static String GUI_ITEMS_NEXTPAGE_NAME;
    public static List<String> GUI_ITEMS_NEXTPAGE_LORE = new ArrayList<>();
    public static short GUI_ITEMS_NEXTPAGE_DURABILITY;
    public static int GUI_ITEMS_NEXTPAGE_SLOT;
    public static Material GUI_ITEMS_PREVIOUSPAGE_MATERIAL;
    public static String GUI_ITEMS_PREVIOUSPAGE_NAME;
    public static List<String> GUI_ITEMS_PREVIOUSPAGE_LORE = new ArrayList<>();
    public static short GUI_ITEMS_PREVIOUSPAGE_DURABILITY;
    public static int GUI_ITEMS_PREVIOUSPAGE_SLOT;
    public static Material GUI_ITEMS_CLOSEGUI_MATERIAL;
    public static String GUI_ITEMS_CLOSEGUI_NAME;
    public static List<String> GUI_ITEMS_CLOSEGUI_LORE = new ArrayList<>();
    public static short GUI_ITEMS_CLOSEGUI_DURABILITY;
    public static int GUI_ITEMS_CLOSEGUI_SLOT;
    public static boolean GUI_FILLER_ENABLED;
    public static Material GUI_FILLER_ITEM_MATERIAL;
    public static String GUI_FILLER_ITEM_NAME;
    public static List<String> GUI_FILLER_ITEM_LORE = new ArrayList<>();
    public static short GUI_FILLER_ITEM_DURABILITY;

    public static String NOT_PLAYER_MESSAGE;
    public static String NO_PERMISSION_MESSAGE;
    public static String USAGE_MESSAGE;
    public static String PLAYER_NOT_FOUND_MESSAGE;
    public static String NO_POUCH_CALLED_THAT_MESSAGE;
    public static String NOT_NUMERIC_MESSAGE;
    public static String AMOUNT_CANNOT_BE_ZERO_MESSAGE;
    public static String GAVE_POUCHES_TO_MESSAGE;
    public static String RECEIVED_POUCHES_FROM_MESSAGE;
    public static String ALREADY_OPENING_POUCH_MESSAGE;
    public static List<String> POUCHES_HELP_MESSAGE = new ArrayList<>();

    public Configuration(Pouches instance) {
        GUI_TITLE = StringUtil.color(instance.getConfig().getString("gui.title"));
        GUI_ROWS =  instance.getConfig().getInt("gui.rows");
        GUI_POUCHES_PER_PAGE =  instance.getConfig().getInt("gui.pouches_per_page");
        GUI_GIVE_POUCH_BY_CLICKING_IF_PERMISSION = instance.getConfig().getBoolean("gui.give_pouch_by_clicking_if_permission");
        GUI_ITEMS_NEXTPAGE_MATERIAL = CompatibilityUtil.getMaterial(instance.getConfig().getString("gui.items.nextpage.material"));
        GUI_ITEMS_NEXTPAGE_NAME = StringUtil.color(instance.getConfig().getString("gui.items.nextpage.name"));
        Configuration.GUI_ITEMS_NEXTPAGE_LORE.clear();
        instance.getConfig().getStringList("gui.items.nextpage.lore").forEach(string -> Configuration.GUI_ITEMS_NEXTPAGE_LORE.add(StringUtil.color(string)));
        GUI_ITEMS_NEXTPAGE_DURABILITY = (short) instance.getConfig().getInt("gui.items.nextpage.durability");
        GUI_ITEMS_NEXTPAGE_SLOT = instance.getConfig().getInt("gui.items.nextpage.slot");
        GUI_ITEMS_PREVIOUSPAGE_MATERIAL = CompatibilityUtil.getMaterial(instance.getConfig().getString("gui.items.previouspage.material"));
        GUI_ITEMS_PREVIOUSPAGE_NAME = StringUtil.color(instance.getConfig().getString("gui.items.previouspage.name"));
        Configuration.GUI_ITEMS_PREVIOUSPAGE_LORE.clear();
        instance.getConfig().getStringList("gui.items.previouspage.lore").forEach(string -> Configuration.GUI_ITEMS_PREVIOUSPAGE_LORE.add(StringUtil.color(string)));
        GUI_ITEMS_PREVIOUSPAGE_DURABILITY = (short) instance.getConfig().getInt("gui.items.previouspage.durability");
        GUI_ITEMS_PREVIOUSPAGE_SLOT = instance.getConfig().getInt("gui.items.previouspage.slot");
        GUI_ITEMS_CLOSEGUI_MATERIAL = CompatibilityUtil.getMaterial(instance.getConfig().getString("gui.items.closegui.material"));
        GUI_ITEMS_CLOSEGUI_NAME = StringUtil.color(instance.getConfig().getString("gui.items.closegui.name"));
        Configuration.GUI_ITEMS_CLOSEGUI_LORE.clear();
        instance.getConfig().getStringList("gui.items.closegui.lore").forEach(string -> Configuration.GUI_ITEMS_CLOSEGUI_LORE.add(StringUtil.color(string)));
        GUI_ITEMS_CLOSEGUI_DURABILITY = (short) instance.getConfig().getInt("gui.items.closegui.durability");
        GUI_ITEMS_CLOSEGUI_SLOT = instance.getConfig().getInt("gui.items.closegui.slot");
        GUI_FILLER_ENABLED = instance.getConfig().getBoolean("gui.filler.enabled");
        GUI_FILLER_ITEM_MATERIAL = CompatibilityUtil.getMaterial(instance.getConfig().getString("gui.filler.item.material"));
        GUI_FILLER_ITEM_NAME = StringUtil.color(instance.getConfig().getString("gui.filler.item.name"));
        Configuration.GUI_FILLER_ITEM_LORE.clear();
        instance.getConfig().getStringList("gui.filler.item.lore").forEach(string -> Configuration.GUI_FILLER_ITEM_LORE.add(StringUtil.color(string)));
        GUI_FILLER_ITEM_DURABILITY = (short) instance.getConfig().getInt("gui.filler.item.durability");

        NOT_PLAYER_MESSAGE = StringUtil.color(instance.getConfig().getString("NOT_PLAYER_MESSAGE"));
        NO_PERMISSION_MESSAGE = StringUtil.color(instance.getConfig().getString("NO_PERMISSION_MESSAGE"));
        USAGE_MESSAGE = StringUtil.color(instance.getConfig().getString("USAGE_MESSAGE"));
        PLAYER_NOT_FOUND_MESSAGE = StringUtil.color(instance.getConfig().getString("PLAYER_NOT_FOUND_MESSAGE"));
        NO_POUCH_CALLED_THAT_MESSAGE = StringUtil.color(instance.getConfig().getString("NO_POUCH_CALLED_THAT_MESSAGE"));
        NOT_NUMERIC_MESSAGE = StringUtil.color(instance.getConfig().getString("NOT_NUMERIC_MESSAGE"));
        AMOUNT_CANNOT_BE_ZERO_MESSAGE = StringUtil.color(instance.getConfig().getString("AMOUNT_CANNOT_BE_ZERO_MESSAGE"));
        GAVE_POUCHES_TO_MESSAGE = StringUtil.color(instance.getConfig().getString("GAVE_POUCHES_TO_MESSAGE"));
        RECEIVED_POUCHES_FROM_MESSAGE = StringUtil.color(instance.getConfig().getString("RECEIVED_POUCHES_FROM_MESSAGE"));
        ALREADY_OPENING_POUCH_MESSAGE = StringUtil.color(instance.getConfig().getString("ALREADY_OPENING_POUCH_MESSAGE"));
        POUCHES_HELP_MESSAGE.clear();
        instance.getConfig().getStringList("POUCHES_HELP_MESSAGE").forEach(string -> POUCHES_HELP_MESSAGE.add(StringUtil.color(string)));
    }
}
