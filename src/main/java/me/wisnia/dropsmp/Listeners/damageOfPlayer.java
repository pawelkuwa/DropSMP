package me.wisnia.dropsmp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;

public class damageOfPlayer implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if (attacker.getType() == EntityType.PLAYER) return;
        Double damage;
        damage = e.getDamage();
        if (e.getDamager().getType() == EntityType.PLAYER) {
            //get stats of damager
            File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
            File f = new File(userdata, File.separator + attacker.getName() + ".yml");
            FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            Double attackerStrength = Double.valueOf(yaml.getInt("stats.strength")) / 100;
            damage = damage * attackerStrength;
        }
        if (victim.getType() == EntityType.PLAYER) {
            //get stats of victim
            File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
            File f = new File(userdata, File.separator + victim.getName() + ".yml");
            FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            Double victimProtection = Double.valueOf(yaml.getInt("stats.protection")) / 100;
            damage = damage - victimProtection;
        }
        e.setDamage(damage);
    }
}
