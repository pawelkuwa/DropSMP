package me.wisnia.dropsmp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class createPlayerYamlFile implements Listener {

    @EventHandler
    public void createPlayerFile(PlayerJoinEvent e) {
        String playerName = e.getPlayer().getName();
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
        File f = new File(userdata, File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("stats");
                playerData.set("stats.strength", 100);
                playerData.set("stats.protection", 100);
                playerData.set("stats.speed", 100);
                playerData.set("stats.hearts", 20);
                playerData.set("banned", false);
                playerData.set("last-drop", 0);
                playerData.save(f);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

}
