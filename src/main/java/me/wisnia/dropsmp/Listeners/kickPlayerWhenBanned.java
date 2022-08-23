package me.wisnia.dropsmp.Listeners;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class kickPlayerWhenBanned implements Listener {

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e) throws IOException {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
        File f = new File(userdata, File.separator + e.getPlayer().getName() + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        if (yaml.getBoolean("banned")) {
            if (yaml.getLong("banned-time") > Instant.now().getEpochSecond()) {
                Instant time = Instant.ofEpochSecond(yaml.getLong("banned-time"));
                e.getPlayer().kickPlayer(String.format("Odbanowany zostaniesz: %s", String.valueOf(time)));
            } else {
                yaml.set("banned", false);
                yaml.save(f);
                File tombdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "tombstone");
                String tombLoc = yaml.getString("tombstone-loc");
                File tombfile = new File(tombdata, File.separator + tombLoc + ".yml");
                FileConfiguration tombYaml = YamlConfiguration.loadConfiguration(tombfile);
                Block tombBlock = e.getPlayer().getWorld().getBlockAt(tombYaml.getInt("tombstone.x"), tombYaml.getInt("tombstone.y"), tombYaml.getInt("tombstone.z"));
                if (tombBlock.getType() == Material.PLAYER_HEAD) {
                    tombBlock.setType(Material.AIR);
                }
                if (tombBlock.getRelative(BlockFace.DOWN, 1).getType() == Material.BEDROCK) {
                    tombBlock.getRelative(BlockFace.DOWN, 1).setType(Material.AIR);
                }
                if (DropSMP.getPlugin().getServer().getPluginManager().isPluginEnabled("DecentHolograms")) {
                    DHAPI.removeHologram("holo::" + e.getPlayer().getName());
                }
                tombfile.delete();
            }
        }
    }

}
