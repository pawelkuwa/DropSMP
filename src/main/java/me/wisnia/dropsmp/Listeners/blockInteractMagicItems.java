package me.wisnia.dropsmp.Listeners;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import me.wisnia.dropsmp.DropSMP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class blockInteractMagicItems implements Listener {


    @EventHandler
    public void PrepareResultEvent(PrepareResultEvent e) {
        if (e.getResult().getItemMeta().getDisplayName() == null) return;
        String name = e.getResult().getItemMeta().getDisplayName();
        if (name.equals(DropSMP.getPlugin().getConfig().getString("insygnia.name"))) {
            e.getInventory().close();
        } else if (name.equals(DropSMP.getPlugin().getConfig().getString("emblematy.strength.name"))) {
            e.getInventory().close();
        } else if (name.equals(DropSMP.getPlugin().getConfig().getString("emblematy.protection.name"))) {
            e.getInventory().close();
        } else if (name.equals(DropSMP.getPlugin().getConfig().getString("emblematy.speed.name"))) {
            e.getInventory().close();
        } else if (name.equals(DropSMP.getPlugin().getConfig().getString("mysterybox.name"))) {
            e.getInventory().close();
        } else if (name.equals(DropSMP.getPlugin().getConfig().getString("magic-items.heart.name"))) {
            e.getInventory().close();
        }
    }

}
