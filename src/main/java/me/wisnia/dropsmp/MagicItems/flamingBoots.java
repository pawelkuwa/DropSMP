package me.wisnia.dropsmp.MagicItems;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class flamingBoots implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer().getInventory().getBoots() == null) return;
        if (e.getPlayer().getInventory().getBoots().getItemMeta().getDisplayName() == null) return;
        if (e.getPlayer().getInventory().getBoots().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.flaming-boots.name"))) {
            if (e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN, 1).getType() == Material.LAVA) {
                Block b = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN, 1);
                b.setType(Material.COBBLESTONE);
                new BukkitRunnable() {
                    public void run() {
                        b.setType(Material.LAVA);
                    }
                }.runTaskLater(DropSMP.getPlugin(), 20 * 5);
            }
        }
    }

}
