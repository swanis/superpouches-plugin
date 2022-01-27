package me.swanis.pouches.pouch.reward;

import java.util.ArrayList;
import java.util.List;

public class Reward {

    private String name;
    private int chance;
    private List<String> commands;

    public Reward(String name) {
        this.name = name;
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
