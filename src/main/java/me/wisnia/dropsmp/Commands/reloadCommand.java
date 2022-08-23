package me.wisnia.dropsmp.Commands;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.wisnia.dropsmp.DropSMP;
import me.wisnia.dropsmp.Listeners.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static dev.dbassett.skullcreator.SkullCreator.itemFromUrl;


public class reloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("dropsmp")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Poprawne użycie: /dropsmp reload");
                return false;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("dropsmp.reload") || !sender.hasPermission("dropsmp.*")) {
                    sender.sendMessage(ChatColor.RED + "Nie masz dostępu do tej komedny!");
                    return false;
                }
                sender.sendMessage(ChatColor.YELLOW + "Rozpoczynam przeładowanie pliku!");
                //reload file
                File cfg = new File(DropSMP.getPlugin().getDataFolder() + "/config.yml");
                if(cfg.exists()) {
                    DropSMP.getPlugin().reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Przeładowano plik konfiguracyjny!");
                } else {
                    DropSMP.getPlugin().getConfig().options().copyDefaults();
                    DropSMP.getPlugin().saveDefaultConfig();
                    sender.sendMessage(ChatColor.GOLD + "Nie znaleziono pliku konfiguracyjnego, tworzę nowy!");
                }
                NamespacedKey insygnia_crafting2 = new NamespacedKey(DropSMP.getPlugin(), "insygnia");
                Bukkit.removeRecipe(insygnia_crafting2);
                if (DropSMP.getPlugin().getConfig().getBoolean("insygnia.crafting.status")) {
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
                    NamespacedKey insygnia_craft_nk = new NamespacedKey(DropSMP.getPlugin(), "insygnia");
                    ShapedRecipe insygnia_craft = new ShapedRecipe(insygnia_craft_nk, insygnia);
                    insygnia_craft.shape("ABC", "DEF", "GHI");
                    Material slot_1 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot1")) != null) {
                        slot_1 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot1"));
                    }
                    insygnia_craft.setIngredient('A', slot_1);
                    Material slot_2 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot2")) != null) {
                        slot_2 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot2"));
                    }
                    insygnia_craft.setIngredient('B', slot_2);
                    Material slot_3 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot3")) != null) {
                        slot_3 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot3"));
                    }
                    insygnia_craft.setIngredient('C', slot_3);
                    Material slot_4 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot4")) != null) {
                        slot_4 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot4"));
                    }
                    insygnia_craft.setIngredient('D', slot_4);
                    Material slot_5 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot5")) != null) {
                        slot_5 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot5"));
                    }
                    insygnia_craft.setIngredient('E', slot_5);
                    Material slot_6 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot6")) != null) {
                        slot_6 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot6"));
                    }
                    insygnia_craft.setIngredient('F', slot_6);
                    Material slot_7 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot7")) != null) {
                        slot_7 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot7"));
                    }
                    insygnia_craft.setIngredient('G', slot_7);
                    Material slot_8 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot8")) != null) {
                        slot_8 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot8"));
                    }
                    insygnia_craft.setIngredient('H', slot_8);
                    Material slot_9 = Material.AIR;
                    if (Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot9")) != null) {
                        slot_9 = Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("insygnia.crafting.slot9"));
                    }
                    insygnia_craft.setIngredient('I', slot_9);
                    Bukkit.addRecipe(insygnia_craft);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("dajprzedmioty")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Nie możesz wykonać tej komendy z konsoli!");
                    return false;
                }
                if (!sender.hasPermission("dropsmp.reload") || !sender.hasPermission("dropsmp.*")) {
                    sender.sendMessage(ChatColor.RED + "Nie masz dostępu do tej komedny!");
                    return false;
                }
                Player p = (Player) sender;

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
                insygnia.setItemMeta(insygnia_meta);

                //emblematy
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

                //sila
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

                //sila
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

                //kilof haste
                ItemStack haste_pickaxe = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.haste-pickaxe.itemtype")));
                ItemMeta haste_pickaxe_meta = haste_pickaxe.getItemMeta();
                haste_pickaxe_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.haste-pickaxe.name"));
                String[] haste_pickaxe_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.haste-pickaxe.lore").toArray(new String[0]);
                haste_pickaxe_meta.setLore(List.of(haste_pickaxe_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.haste-pickaxe.unbreaking-1")) {
                    haste_pickaxe_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                haste_pickaxe.setItemMeta(haste_pickaxe_meta);

                //kilof 3x3
                ItemStack x3_pickaxe = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.3x3-pickaxe.itemtype")));
                ItemMeta x3_pickaxe_meta = x3_pickaxe.getItemMeta();
                x3_pickaxe_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.3x3-pickaxe.name"));
                String[] x3_pickaxe_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.3x3-pickaxe.lore").toArray(new String[0]);
                x3_pickaxe_meta.setLore(List.of(x3_pickaxe_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.3x3-pickaxe.unbreaking-1")) {
                    x3_pickaxe_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                x3_pickaxe.setItemMeta(x3_pickaxe_meta);

                //zatruty miecz
                ItemStack poison_sword = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.poison-sword.itemtype")));
                ItemMeta poison_sword_meta = poison_sword.getItemMeta();
                poison_sword_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.poison-sword.name"));
                String[] poison_sword_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.poison-sword.lore").toArray(new String[0]);
                poison_sword_meta.setLore(List.of(poison_sword_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.poison-sword.sharpness-1")) {
                    poison_sword_meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                }
                poison_sword.setItemMeta(poison_sword_meta);

                //magiczna siekiera
                ItemStack magic_axe = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.magic-axe.itemtype")));
                ItemMeta magic_axe_meta = magic_axe.getItemMeta();
                magic_axe_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.magic-axe.name"));
                String[] magic_axe_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.magic-axe.lore").toArray(new String[0]);
                magic_axe_meta.setLore(List.of(magic_axe_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.magic-axe.unbreaking-1")) {
                    magic_axe_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                magic_axe.setItemMeta(magic_axe_meta);

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

                //miecz szybkosci
                ItemStack sword_speed = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.sword-speed.itemtype")));
                ItemMeta sword_speed_meta = sword_speed.getItemMeta();
                sword_speed_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.sword-speed.name"));
                String[] sword_speed_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.sword-speed.lore").toArray(new String[0]);
                sword_speed_meta.setLore(List.of(sword_speed_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.sword-speed.sharpness-1")) {
                    sword_speed_meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                }
                sword_speed.setItemMeta(sword_speed_meta);

                //plomienne buty
                ItemStack flaming_boots = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.flaming-boots.itemtype")));
                ItemMeta flaming_boots_meta = flaming_boots.getItemMeta();
                flaming_boots_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.flaming-boots.name"));
                String[] flaming_boots_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.flaming-boots.lore").toArray(new String[0]);
                flaming_boots_meta.setLore(List.of(flaming_boots_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.flaming-boots.unbreaking-1")) {
                    flaming_boots_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                flaming_boots.setItemMeta(flaming_boots_meta);

                //plemienna zbroja
                ItemStack tribal_chest = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.tribal-chest.itemtype")));
                ItemMeta tribal_chest_meta = tribal_chest.getItemMeta();
                tribal_chest_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.tribal-chest.name"));
                String[] tribal_chest_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.tribal-chest.lore").toArray(new String[0]);
                tribal_chest_meta.setLore(List.of(tribal_chest_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.tribal-chest.unbreaking-1")) {
                    tribal_chest_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                tribal_chest.setItemMeta(tribal_chest_meta);

                //miecz mdlosci
                ItemStack nausea_sword = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.nausea-sword.itemtype")));
                ItemMeta nausea_sword_meta = nausea_sword.getItemMeta();
                nausea_sword_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.nausea-sword.name"));
                String[] nausea_sword_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.nausea-sword.lore").toArray(new String[0]);
                nausea_sword_meta.setLore(List.of(nausea_sword_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.nausea-sword.sharpness-1")) {
                    nausea_sword_meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                }
                nausea_sword.setItemMeta(nausea_sword_meta);

                //miecz krwiopijcy
                ItemStack lifesteal_sword = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.lifesteal-sword.itemtype")));
                ItemMeta lifesteal_sword_meta = lifesteal_sword.getItemMeta();
                lifesteal_sword_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.lifesteal-sword.name"));
                String[] lifesteal_sword_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.lifesteal-sword.lore").toArray(new String[0]);
                lifesteal_sword_meta.setLore(List.of(lifesteal_sword_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.lifesteal-sword.sharpness-1")) {
                    lifesteal_sword_meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                }
                lifesteal_sword.setItemMeta(lifesteal_sword_meta);

                //siekiera spowolnienia
                ItemStack slowness_axe = new ItemStack(Material.matchMaterial(DropSMP.getPlugin().getConfig().getString("magic-items.slowness-axe.itemtype")));
                ItemMeta slowness_axe_meta = slowness_axe.getItemMeta();
                slowness_axe_meta.setDisplayName(DropSMP.getPlugin().getConfig().getString("magic-items.slowness-axe.name"));
                String[] slowness_axe_lore = DropSMP.getPlugin().getConfig().getStringList("magic-items.slowness-axe.lore").toArray(new String[0]);
                slowness_axe_meta.setLore(List.of(slowness_axe_lore));
                if (DropSMP.getPlugin().getConfig().getBoolean("magic-items.slowness-axe.unbreaking-1")) {
                    slowness_axe_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                }
                slowness_axe.setItemMeta(slowness_axe_meta);

                //gui
                Inventory gui = Bukkit.createInventory(p, 18, ChatColor.DARK_AQUA + "Przedmioty DROPSMP");
                ItemStack[] gui_items = {insygnia, sila, obrona, speed, lootbox, haste_pickaxe, x3_pickaxe, poison_sword, magic_axe, heart, sword_speed, flaming_boots, tribal_chest, nausea_sword, lifesteal_sword, slowness_axe};
                gui.setContents(gui_items);
                p.openInventory(gui);
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("edytujdrop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Nie możesz wykonać tej komendy z konsoli!");
                return false;
            }
            if (!sender.hasPermission("dropsmp.edytujdrop") || !sender.hasPermission("dropsmp.*")) {
                sender.sendMessage(ChatColor.RED + "Nie masz dostępu do tej komedny!");
                return false;
            }
            Player p = (Player) sender;
            List<String> drops = DropSMP.getPlugin().getConfig().getStringList("mysterybox.drops");
            List<ItemStack> items = new ArrayList<ItemStack>();
            for (int i = 0; i < drops.size(); i++) {
                ItemStack it = ItemStackSerializer.deserialize(drops.get(i));
                items.add(it);
            }

            //gui
            Inventory gui = Bukkit.createInventory(p, 54, ChatColor.GOLD + "Edycja dropu");
            ItemStack[] gui_items = new ItemStack[items.size()];
            items.toArray(gui_items);
            gui.setContents(gui_items);
            p.openInventory(gui);
            return true;
        }
        return false;
    }
}
