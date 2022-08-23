package me.wisnia.dropsmp.Listeners;

import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.text.MessageFormat;

public class sendActionBarStats implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DropSMP.getPlugin(), new Runnable() {
            @Override
            public void run() {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + e.getPlayer().getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                String[] values = { yaml.getString("stats.strength"), yaml.getString("stats.protection"), yaml.getString("stats.speed") };
                String actionbar = MessageFormat.format(DropSMP.getPlugin().getConfig().getString("actionbar"), values);
                e.getPlayer().sendActionBar(actionbar);
            }
        }, 0L, 20L);
    }
}
