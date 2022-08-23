package me.wisnia.dropsmp.MagicItems;

import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class heart implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e) throws IOException {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if ((a == Action.PHYSICAL) || (e.getItem()) == null) return;
        if ((a == Action.RIGHT_CLICK_AIR) || (a == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getItemMeta().getDisplayName() == null) return;
            if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.heart.name"))) {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + p.getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (yaml.getInt("stats.hearts") / 2 == DropSMP.getPlugin().getConfig().getInt("magic-items.heart.limit")) {
                    p.sendMessage(ChatColor.RED + "Osiągnąłeś limit serc!");
                    return;
                }
                yaml.set("stats.hearts", yaml.getInt("stats.hearts") +2);
                Double hearts = Double.valueOf(yaml.getInt("stats.hearts"));
                yaml.save(f);
                p.setMaxHealth(hearts);
                //dodatkowe serce
                ItemStack heart = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.heart.itemtype")));
                ItemMeta heart_meta = heart.getItemMeta();
                heart_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.heart.name"));
                String[] heart_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.heart.lore").toArray(new String[0]);
                heart_meta.setLore(List.of(heart_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.heart.unbreaking-1")) {
                    heart_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                heart.setItemMeta(heart_meta);
                p.getInventory().removeItem(heart);
                p.sendMessage(ChatColor.GREEN + "Dodano dodatkowe serce!");
            }
        }
    }

}
