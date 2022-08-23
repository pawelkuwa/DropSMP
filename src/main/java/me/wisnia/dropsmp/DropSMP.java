package me.wisnia.dropsmp;

import eu.decentsoftware.holograms.api.DHAPI;
import me.wisnia.dropsmp.Commands.reloadCommand;
import me.wisnia.dropsmp.Listeners.*;
import me.wisnia.dropsmp.MagicItems.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class DropSMP extends JavaPlugin implements Listener {

    private static DropSMP plugin;
    private static DropSMP instance;

    @Override
    public void onEnable() {
        plugin = this;
        instance = this;
        File cfg = new File(this.getDataFolder() + "/config.yml");
        if (!cfg.exists()) {
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
        } else {
            this.reloadConfig();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getServer().getPluginManager().registerEvents(new Placeholders(), this);
            new Placeholders().register();
            getServer().getLogger().info("Znaleziono PlaceholderAPI, tworzę placeholdery...");
        }

        NamespacedKey insygnia_crafting2 = new NamespacedKey(this, "insygnia");
        Bukkit.removeRecipe(insygnia_crafting2);
        if (this.getConfig().getBoolean("insygnia.crafting.status")) {
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
            insygnia.setItemMeta(insygnia_meta);

            //crafting
            NamespacedKey insygnia_craft_nk = new NamespacedKey(this, "insygnia");
            ShapedRecipe insygnia_craft = new ShapedRecipe(insygnia_craft_nk, insygnia);
                insygnia_craft.shape("ABC", "DEF", "GHI");
                Material slot_1 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot1")) != null) {
                    slot_1 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot1"));
                }
                insygnia_craft.setIngredient('A', slot_1);
                Material slot_2 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot2")) != null) {
                    slot_2 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot2"));
                }
                insygnia_craft.setIngredient('B', slot_2);
                Material slot_3 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot3")) != null) {
                    slot_3 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot3"));
                }
                insygnia_craft.setIngredient('C', slot_3);
                Material slot_4 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot4")) != null) {
                    slot_4 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot4"));
                }
                insygnia_craft.setIngredient('D', slot_4);
                Material slot_5 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot5")) != null) {
                    slot_5 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot5"));
                }
                insygnia_craft.setIngredient('E', slot_5);
                Material slot_6 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot6")) != null) {
                    slot_6 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot6"));
                }
                insygnia_craft.setIngredient('F', slot_6);
                Material slot_7 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot7")) != null) {
                    slot_7 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot7"));
                }
                insygnia_craft.setIngredient('G', slot_7);
                Material slot_8 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot8")) != null) {
                    slot_8 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot8"));
                }
                insygnia_craft.setIngredient('H', slot_8);
                Material slot_9 = Material.AIR;
                if (Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot9")) != null) {
                    slot_9 = Material.matchMaterial(this.getConfig().getString("insygnia.crafting.slot9"));
                }
                insygnia_craft.setIngredient('I', slot_9);
                Bukkit.addRecipe(insygnia_craft);
        }

        getLogger().info("Plugin został włączony!");
        getServer().getPluginManager().registerEvents(new sendActionBarStats(), this);
        getServer().getPluginManager().registerEvents(new createPlayerYamlFile(), this);
        getServer().getPluginManager().registerEvents(new removeStatsWhenDead(), this);
        getServer().getPluginManager().registerEvents(new kickPlayerWhenBanned(), this);
        getServer().getPluginManager().registerEvents(new damageOfPlayer(), this);
        getServer().getPluginManager().registerEvents(new magicItemsUsage(), this);
        getServer().getPluginManager().registerEvents(new ItemStackSerializer(), this);
        getServer().getPluginManager().registerEvents(new blockInteractMagicItems(), this);
        getServer().getPluginManager().registerEvents(new saveDropsEvent(), this);
        getServer().getPluginManager().registerEvents(new tombstoneUsage(), this);

        //magic items
        getServer().getPluginManager().registerEvents(new swordSpeed(), this);
        getServer().getPluginManager().registerEvents(new magicAxe(), this);
        getServer().getPluginManager().registerEvents(new heart(), this);
        getServer().getPluginManager().registerEvents(new swordSpeed(), this);
        getServer().getPluginManager().registerEvents(new flamingBoots(), this);
        getServer().getPluginManager().registerEvents(new tribalChest(), this);
        getServer().getPluginManager().registerEvents(new nauseaSword(), this);
        getServer().getPluginManager().registerEvents(new lifestealSword(), this);
        getServer().getPluginManager().registerEvents(new slownessAxe(), this);

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("dropsmp").setExecutor(new reloadCommand());
        getCommand("dropsmp").setTabCompleter(new commandTabComplete());
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DecentHolograms")) {
            File ts = new File(this.getDataFolder(), File.separator + "tombstone");
            File[] tombstones = ts.listFiles();;
            for (int i = 0; i < tombstones.length; i++) {
                File tombFile = new File(String.valueOf(tombstones[i]));
                String tombName = tombFile.getName();
                File tombData = new File(Bukkit.getServer().getPluginManager().getPlugin("DropSMP").getDataFolder(), File.separator + "tombstone");
                File tombPath = new File(tombData, File.separator + tombName);
                FileConfiguration tombYaml = YamlConfiguration.loadConfiguration(tombPath);
                if (DHAPI.getHologram("holo::" + tombYaml.getString("tombstone.owner")) == null) {
                    Block block = Bukkit.getWorld("world").getBlockAt(tombYaml.getInt("tombstone.x"), tombYaml.getInt("tombstone.y"), tombYaml.getInt("tombstone.z"));
                    List<String> lines = Arrays.asList(ChatColor.DARK_GREEN + "Pomnik", ChatColor.GREEN + tombYaml.getString("tombstone.owner"));
                    DHAPI.createHologram("holo::" + tombYaml.getString("tombstone.owner"), block.getLocation().add(0.5, 1.25, 0.5), false, lines);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        File userdata = new File(this.getDataFolder(), File.separator + "data");
        File f = new File(userdata, File.separator + e.getPlayer().getName() + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        float speed = (float) yaml.getInt("stats.speed") / 500;
        e.getPlayer().setWalkSpeed(speed);
        Double hearts = Double.valueOf(yaml.getInt("stats.hearts"));
        e.getPlayer().setMaxHealth(hearts);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin został wyłączony");

    }

    public static DropSMP getPlugin() {

        return plugin;
    }

    public static DropSMP getInstance() {
        return instance;
    }

}
