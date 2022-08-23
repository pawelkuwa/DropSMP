package me.wisnia.dropsmp.Utils;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class rmTombstoneExternalBan implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //get player
        Player p = e.getPlayer();
        //get player yaml
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
        File f = new File(userdata, File.separator + p.getName() + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        //get tomb loc as string
        String tombLoc = yaml.getString("tombstone-loc");
        //get tomb yaml
        File tombdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "tombstone");
        File tombfile = new File(tombdata, File.separator + tombLoc + ".yml");
        //check tomb yaml file
        if (tombfile.exists()) {
            //load tomb yaml file
            FileConfiguration tombYaml = YamlConfiguration.loadConfiguration(tombfile);
            //delete utils
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
