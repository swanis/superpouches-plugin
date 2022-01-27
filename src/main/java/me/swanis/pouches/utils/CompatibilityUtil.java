package me.swanis.pouches.utils;

import org.bukkit.Material;

public class CompatibilityUtil {

    public static Material getMaterial(String material) {
        Material mat = null;

        try {
            mat = Material.valueOf(material);
        } catch (IllegalArgumentException e) {
            try {
                if (material.equals("STAINED_GLASS_PANE")) {
                    mat = Material.valueOf("GRAY_STAINED_GLASS_PANE");
                } else {
                    mat = Material.valueOf("LEGACY_" + material);
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }

        return mat;
    }
}
