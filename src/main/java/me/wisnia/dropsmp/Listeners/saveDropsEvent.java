package me.wisnia.dropsmp.Listeners;

import me.wisnia.dropsmp.DropSMP;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class saveDropsEvent implements Listener {

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) throws IOException {
        if (e.getView().getTitle().equals(ChatColor.GOLD + "Edycja dropu")) {
            ItemStack[] items = e.getInventory().getContents();
            List<String> items_cfg = new ArrayList<String>();
            for (ItemStack i : items) {
                if (i != null) {
                    items_cfg.add(ItemStackSerializer.serialize(i));
                }
            }
            DropSMP.getPlugin().getConfig().set("mysterybox.drops", items_cfg);
            DropSMP.getPlugin().saveConfig();
            DropSMP.getPlugin().reloadConfig();
            e.getPlayer().sendMessage(ChatColor.GOLD + "Zapisano drop mysteryboxa");
        }
    }

}
