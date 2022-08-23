package me.wisnia.dropsmp.Listeners;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Placeholders extends PlaceholderExpansion implements Listener {

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) {
            return "";
        }
        if (params.equals("strength")) {
            File userdata = new File(DropSMP.getPlugin().getDataFolder(), File.separator + "data");
            File f = new File(userdata, File.separator + p.getName() + ".yml");
            FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            return yaml.getString("stats.strength");
        } else if (params.equals("protection")) {
            File userdata = new File(DropSMP.getPlugin().getDataFolder(), File.separator + "data");
            File f = new File(userdata, File.separator + p.getName() + ".yml");
            FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            return yaml.getString("stats.protection");
        } else if (params.equals("speed")) {
            File userdata = new File(DropSMP.getPlugin().getDataFolder(), File.separator + "data");
            File f = new File(userdata, File.separator + p.getName() + ".yml");
            FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            return yaml.getString("stats.speed");
        }
        return "";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dropsmp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "_wisnia";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1";
    }
}
