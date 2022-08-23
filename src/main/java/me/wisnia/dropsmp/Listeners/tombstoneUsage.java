package me.wisnia.dropsmp.Listeners;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class tombstoneUsage implements Listener {

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) throws IOException {
        if (e.getBlock().getType() == Material.PLAYER_HEAD) {
            String loc = e.getBlock().getX() + "," + e.getBlock().getY() + "," + e.getBlock().getZ();
            File tombStone = new File(DropSMP.getPlugin().getDataFolder() + "/tombstone/" + loc + ".yml");
            if (tombStone.exists()) {
                e.setCancelled(true);
                File tombData = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "tombstone");
                File tombFile = new File(tombData, File.separator + loc + ".yml");
                FileConfiguration tombYaml = YamlConfiguration.loadConfiguration(tombFile);
                DHAPI.removeHologram("holo::" + tombYaml.getString("tombstone.owner"));
                e.getBlock().setType(Material.AIR);
                if (e.getBlock().getRelative(BlockFace.DOWN, 1).getType() != Material.AIR) {
                    e.getBlock().getRelative(BlockFace.DOWN, 1).setType(Material.AIR);
                }
                //load player
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + tombYaml.getString("tombstone.owner") + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (DropSMP.getPlugin().getConfig().getBoolean("use-external-unban-command")) {
                    String command = MessageFormat.format(DropSMP.getPlugin().getConfig().getString("external-unban-command"), tombYaml.getString("tombstone.owner"));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                } else {
                    yaml.set("banned", false);
                    yaml.save(f);
                }
                e.getPlayer().sendMessage(ChatColor.GREEN + "Gracz " + tombYaml.getString("tombstone.owner") + " został wskrzeszony!");
                e.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
                tombFile.delete();
            }
        }
    }
}
