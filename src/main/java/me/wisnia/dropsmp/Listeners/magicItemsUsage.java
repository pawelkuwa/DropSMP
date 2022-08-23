package me.wisnia.dropsmp.Listeners;

import me.wisnia.dropsmp.DropSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static dev.dbassett.skullcreator.SkullCreator.itemFromUrl;

public class magicItemsUsage implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DropSMP.getPlugin(), () -> {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.haste-pickaxe.itemtype"))){
                if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(DropSMP.getPlugin().getConfig().getString("magic-items.haste-pickaxe.name"))) {
                    e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
                    PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 1);
                    e.getPlayer().addPotionEffects(Collections.singleton((effect)));
                }
            }
        },20L, 20L);
    }

    @EventHandler
    public void use3x3Pickaxe(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.3x3-pickaxe.itemtype"))) {
            return;
        }
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.3x3-pickaxe.name"))) {
            for (int x = e.getBlock().getX() - 1; x <= e.getBlock().getX() + 1; x++) {
                for (int y = e.getBlock().getY() - 1; y <= e.getBlock().getY() + 1; y++) {
                    for (int z = e.getBlock().getZ() - 1; z <= e.getBlock().getZ() + 1; z++) {
                        Block blockToDestroy = e.getPlayer().getWorld().getBlockAt(x, y, z);
                        if (blockToDestroy.getType() != Material.BEDROCK) {
                            blockToDestroy.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                }
            }
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            item.setDurability((short) ((short) item.getDurability() + 1));
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e) throws IOException {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if ((a == Action.PHYSICAL) || (e.getItem()) == null) return;
        if ((a == Action.RIGHT_CLICK_AIR) || (a == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getItemMeta().getDisplayName() == null) return;
            if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("insygnia.name"))) {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + p.getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (yaml.getInt("stats.strength") == 100) {
                    if (yaml.getInt("stats.protection") == 100) {
                        if (yaml.getInt("stats.speed") == 100) {
                            p.sendMessage(ChatColor.RED + "Twoje wszystkie statystyki wynoszą już 100%!");
                            return;
                        }
                    }
                }
                yaml.set("stats.strength", 100);
                yaml.set("stats.protection", 100);
                yaml.set("stats.speed", 100);
                yaml.save(f);
                float speed = (float) 0.2;
                p.setWalkSpeed(speed);
                //insygnia
                String s = (DropSMP.getPlugin().getConfig().getString("insygnia.itemtype"));
                Material m = Material.matchMaterial(s);
                ItemStack insygnia = new ItemStack(m);
                ItemMeta insygnia_meta = insygnia.getItemMeta();
                insygnia_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("insygnia.name"));
                String[] insygnia_lore = DropSMP.getPlugin().getConfig().getStringList("insygnia.lore").toArray(new String[0]);
                insygnia_meta.setLore(List.of(insygnia_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("insygnia.glowing")) {
                    insygnia_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                e.setCancelled(true);
                insygnia.setItemMeta(insygnia_meta);
                p.getInventory().removeItem(insygnia);
                p.sendMessage(ChatColor.GREEN + "Odnowiłeś wszystkie statystyki!");
            } else if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("emblematy.strength.name"))) {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + p.getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (yaml.getInt("stats.strength") == DropSMP.getPlugin().getConfig().getInt("emblematy.strength.limit")) {
                    p.sendMessage(ChatColor.RED + "Osiągnałeś limit siły");
                    return;
                }
                yaml.set("stats.strength", yaml.getInt("stats.strength") + DropSMP.getPlugin().getConfig().getInt("emblematy.strength.gain-value"));
                yaml.save(f);
                //sila
                String s1 = (DropSMP.getPlugin().getConfig().getString("emblematy.strength.itemtype"));
                Material m1 = Material.matchMaterial(s1);
                ItemStack sila = new ItemStack(m1);
                ItemMeta sila_meta = sila.getItemMeta();
                sila_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("emblematy.strength.name"));
                String[] sila_lore = DropSMP.getPlugin().getConfig().getStringList("emblematy.strength.lore").toArray(new String[0]);
                sila_meta.setLore(List.of(sila_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("emblematy.strength.glowing")) {
                    sila_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                sila.setItemMeta(sila_meta);
                e.setCancelled(true);
                p.getInventory().removeItem(sila);
                p.sendMessage(ChatColor.GREEN + "Dodałeś " + DropSMP.getPlugin().getConfig().getInt("emblematy.strength.gain-value") + "% do statystyki siła!");
            } else if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("emblematy.protection.name"))) {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + p.getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (yaml.getInt("stats.protection") == DropSMP.getPlugin().getConfig().getInt("emblematy.protection.limit")) {
                    p.sendMessage(ChatColor.RED + "Osiągnałeś limit obrony");
                    return;
                }
                yaml.set("stats.protection", yaml.getInt("stats.protection") + DropSMP.getPlugin().getConfig().getInt("emblematy.protection.gain-value"));
                yaml.save(f);
                //obrona
                String s2 = (DropSMP.getPlugin().getConfig().getString("emblematy.protection.itemtype"));
                Material m2 = Material.matchMaterial(s2);
                ItemStack obrona = new ItemStack(m2);
                ItemMeta obrona_meta = obrona.getItemMeta();
                obrona_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("emblematy.protection.name"));
                String[] obrona_lore = DropSMP.getPlugin().getConfig().getStringList("emblematy.protection.lore").toArray(new String[0]);
                obrona_meta.setLore(List.of(obrona_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("emblematy.protection.glowing")) {
                    obrona_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                obrona.setItemMeta(obrona_meta);
                e.setCancelled(true);
                p.getInventory().removeItem(obrona);
                p.sendMessage(ChatColor.GREEN + "Dodałeś " + DropSMP.getPlugin().getConfig().getInt("emblematy.protection.gain-value") + "% do statystyki obrona!");
            } else if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("emblematy.speed.name"))) {
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "data");
                File f = new File(userdata, File.separator + p.getName() + ".yml");
                FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                if (yaml.getInt("stats.speed") == DropSMP.getPlugin().getConfig().getInt("emblematy.speed.limit")) {
                    p.sendMessage(ChatColor.RED + "Osiągnałeś limit szybkości");
                    return;
                }
                Integer speed_int = yaml.getInt("stats.speed") + DropSMP.getPlugin().getConfig().getInt("emblematy.speed.gain-value");
                yaml.set("stats.speed", speed_int);
                yaml.save(f);
                //speed
                String s3 = (DropSMP.getPlugin().getConfig().getString("emblematy.speed.itemtype"));
                Material m3 = Material.matchMaterial(s3);
                ItemStack speed = new ItemStack(m3);
                ItemMeta speed_meta = speed.getItemMeta();
                speed_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("emblematy.speed.name"));
                String[] speed_lore = DropSMP.getPlugin().getConfig().getStringList("emblematy.speed.lore").toArray(new String[0]);
                speed_meta.setLore(List.of(speed_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("emblematy.speed.glowing")) {
                    speed_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                speed.setItemMeta(speed_meta);
                e.setCancelled(true);
                p.getInventory().removeItem(speed);
                p.sendMessage(ChatColor.GREEN + "Dodałeś " + DropSMP.getPlugin().getConfig().getInt("emblematy.speed.gain-value") + "% do statystyki szybkość!");
                Float speed_walk;
                speed_walk = (float) speed_int / 500;
                p.setWalkSpeed(speed_walk);
            } else if (e.getItem().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("mysterybox.name"))) {
                //mysterybox
                ItemStack lootbox = itemFromUrl(DropSMP.getPlugin().getConfig().getString("mysterybox.skin-url"));
                ItemMeta lootbox_meta = lootbox.getItemMeta();
                lootbox_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("mysterybox.name"));
                String[] lootbox_lore = DropSMP.getPlugin().getConfig().getStringList("mysterybox.lore").toArray(new String[0]);
                lootbox_meta.setLore(List.of(lootbox_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("mysterybox.glowing")) {
                    lootbox_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                lootbox.setItemMeta(lootbox_meta);
                e.setCancelled(true);
                p.getInventory().removeItem(lootbox);
                p.sendMessage(ChatColor.GREEN + "Otworzyleś lootbox!");
                //code
                List<String> drops = DropSMP.getPlugin().getConfig().getStringList("mysterybox.drops");
                List<ItemStack> items = new ArrayList<ItemStack>();
                for (int i = 0; i < drops.size(); i++) {
                    ItemStack it = ItemStackSerializer.deserialize(drops.get(i));
                    items.add(it);
                }
                Random generator = new Random();
                int random = generator.nextInt(items.size());
                e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation().add(0,1,0), items.get(random));
            }
        }
    }
}
