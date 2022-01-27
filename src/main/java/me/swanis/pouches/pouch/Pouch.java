package me.swanis.pouches.pouch;

import me.swanis.pouches.pouch.reward.Reward;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Pouch {

    private String configKey;
    private String name;
    private boolean number;
    private String message;
    private boolean titleEnabled;
    private String titleMessage;
    private String titleSubMessage;
    private boolean titleRight;
    private String titleColor;
    private int titleFadeIn;
    private int titleStay;
    private int titleFadeOut;
    private int titleUpdate;
    private boolean soundEnabled;
    private Sound soundType;
    private int soundVolume;
    private int soundPitch;
    private List<String> commands;
    private List<Reward> rewards;
    private long min;
    private long max;
    private boolean formatEnabled;
    private NumberFormat numberFormat;
    private ItemStack item;

    public Pouch(String configKey, boolean number) {
        this.configKey = configKey;
        this.number = number;
        this.commands = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    public String getConfigKey() {
        return configKey;
    }

    public boolean isNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isTitleEnabled() {
        return titleEnabled;
    }

    public void setTitleEnabled(boolean titleEnabled) {
        this.titleEnabled = titleEnabled;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public String getTitleSubMessage() {
        return titleSubMessage;
    }

    public void setTitleSubMessage(String titleSubMessage) {
        this.titleSubMessage = titleSubMessage;
    }

    public boolean isTitleRight() {
        return titleRight;
    }

    public void setTitleRight(boolean titleRight) {
        this.titleRight = titleRight;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public int getTitleFadeIn() {
        return titleFadeIn;
    }

    public void setTitleFadeIn(int titleFadeIn) {
        this.titleFadeIn = titleFadeIn;
    }

    public int getTitleStay() {
        return titleStay;
    }

    public void setTitleStay(int titleStay) {
        this.titleStay = titleStay;
    }

    public int getTitleFadeOut() {
        return titleFadeOut;
    }

    public void setTitleFadeOut(int titleFadeOut) {
        this.titleFadeOut = titleFadeOut;
    }

    public int getTitleUpdate() {
        return titleUpdate;
    }

    public void setTitleUpdate(int titleUpdate) {
        this.titleUpdate = titleUpdate;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public Sound getSoundType() {
        return soundType;
    }

    public void setSoundType(Sound soundType) {
        this.soundType = soundType;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getSoundPitch() {
        return soundPitch;
    }

    public void setSoundPitch(int soundPitch) {
        this.soundPitch = soundPitch;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> commands) {
        this.rewards = commands;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public boolean isFormatEnabled() {
        return formatEnabled;
    }

    public void setFormatEnabled(boolean formatEnabled) {
        this.formatEnabled = formatEnabled;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
