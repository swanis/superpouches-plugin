package me.swanis.pouches.utils;

import org.bukkit.Sound;

/**
 * Version independent spigot sounds.
 */
public enum Sounds {
    ANVIL_LAND("ANVIL_LAND", "BLOCK_ANVIL_LAND", "BLOCK_ANVIL_LAND"),
    GLASS("GLASS", "BLOCK_GLASS_BREAK", "BLOCK_GLASS_BREAK"),
    NOTE_PLING("NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING"),
    ORB_PICKUP("ORB_PICKUP", "ENTITY_EXPERIENCE_ORB_PICKUP", "ENTITY_EXPERIENCE_ORB_PICKUP");

    private String pre19sound;
    private String post19sound;
    private String post112sound;

    Sounds(String pre19sound, String post19sound, String post112sound) {
        this.pre19sound = pre19sound;
        this.post19sound = post19sound;
        this.post112sound = post112sound;
    }

    public Sound bukkitSound() {
        try {
            //Try pre 1.9 sound
            return Sound.valueOf(pre19sound);
        } catch (IllegalArgumentException e) {
            try {
                //Try post 1.9 sound
                return Sound.valueOf(post19sound);
            } catch (IllegalArgumentException ex) {
                //Try post 1.12 sound
                return Sound.valueOf(post112sound);
            }
        }
    }
}