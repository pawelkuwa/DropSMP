package me.wisnia.dropsmp.Listeners;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static dev.dbassett.skullcreator.SkullCreator.itemFromUrl;

public class removeStatsWhenDead implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws IOException {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
        File f = new File(userdata, File.separator + e.getPlayer().getName() + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        Boolean drop_loot = true;
        if (yaml.getInt("last-drop") >= Instant.now().getEpochSecond()) {
            drop_loot = false;
        }
        if (drop_loot) {
            Random rand = new Random();
            int chance = rand.nextInt(100) +1;
            if (chance <= DropSMP.getPlugin().getConfig().getInt("mysterybox.chance-for-drop")) {
                ItemStack lootbox = itemFromUrl(DropSMP.getPlugin().getConfig().getString("mysterybox.skin-url"));
                ItemMeta lootbox_meta = lootbox.getItemMeta();
                lootbox_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("mysterybox.name"));
                String[] lootbox_lore = DropSMP.getPlugin().getConfig().getStringList("mysterybox.lore").toArray(new String[0]);
                lootbox_meta.setLore(List.of(lootbox_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("mysterybox.glowing")) {
                    lootbox_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                lootbox.setItemMeta(lootbox_meta);
                e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation().add(0, 1, 0), lootbox);
                yaml.set("last-drop", Instant.now().getEpochSecond() + DropSMP.getPlugin().getConfig().getInt("next-drop-time"));
                yaml.save(f);
            }
        }
        if (yaml.getInt("stats.strength") > 0) {
            yaml.set("stats.strength", yaml.getInt("stats.strength") - 10);
            yaml.save(f);
        }
        if (yaml.getInt("stats.protection") > 0) {
            yaml.set("stats.protection", yaml.getInt("stats.protection") - 10);
            yaml.save(f);
        }
        if (yaml.getInt("stats.speed") > 0) {
            yaml.set("stats.speed", yaml.getInt("stats.speed") - 10);
            yaml.save(f);
            float speed = (float) yaml.getInt("stats.speed") / 500;
            e.getPlayer().setWalkSpeed(speed);
        }
        if (yaml.getInt("stats.hearts") > 20) {
            yaml.set("stats.hearts", yaml.getInt("stats.hearts") -2);
            yaml.save(f);
            Double hearts = Double.valueOf(yaml.getInt("stats.hearts"));
            e.getPlayer().setMaxHealth(hearts);
        }
        if (yaml.getInt("stats.strength") <= 0) {
            if (yaml.getInt("stats.protection") <= 0) {
                if (yaml.getInt("stats.speed") <= 0) {
                    yaml.set("stats.strength", 100);
                    yaml.set("stats.protection", 100);
                    yaml.set("stats.speed", 100);
                    yaml.set("banned", true);

                    //totem
                    Block block = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP, 1);
                    String blockString = String.valueOf(block.getX()) + "," + String.valueOf(block.getY()) + "," + String.valueOf(block.getZ());
                    block.setType(Material.PLAYER_HEAD);
                    block.getRelative(BlockFace.DOWN, 1).setType(Material.BEDROCK);
                    if (DropSMP.getPlugin().getServer().getPluginManager().isPluginEnabled("DecentHolograms")) {
                        List<String> lines = Arrays.asList(ChatColor.DARK_GREEN + "Pomnik", ChatColor.GREEN + e.getPlayer().getName());
                        String holoID = String.format("holo::%s", e.getPlayer().getName());
                        DHAPI.createHologram(holoID, block.getLocation().add(0.5, 1.25, 0.5), false, lines);
                    }
                    File blockdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "tombstone");
                    File blockfile = new File(blockdata, File.separator + blockString + ".yml");
                    FileConfiguration blockYaml = YamlConfiguration.loadConfiguration(blockfile);
                    blockYaml.createSection("tombstone");
                    blockYaml.set("tombstone.owner", e.getPlayer().getName());
                    blockYaml.set("tombstone.x", block.getX());
                    blockYaml.set("tombstone.y", block.getY());
                    blockYaml.set("tombstone.z", block.getZ());
                    blockYaml.save(blockfile);
                    e.getPlayer().getInventory().clear();
                    if(DropSMP.getPlugin().getConfig().getBoolean("use-external-ban-command")) {
                        String command = MessageFormat.format(DropSMP.getPlugin().getConfig().getString("external-ban-command"), e.getPlayer().getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    } else {
                        int ban_time_hours = DropSMP.getPlugin().getConfig().getInt("ban-time-hours");
                        long time = Instant.now().getEpochSecond() + ban_time_hours * 3600L;
                        yaml.set("banned-time", time);
                        yaml.set("tombstone-loc", blockString);
                        e.getPlayer().kickPlayer("Zginąłeś, zostałeś zbanowany na 24 godziny!");
                    }
                }
            }
        }
        yaml.save(f);
    }
}
