package me.wisnia.dropsmp.MagicItems;

import me.wisnia.dropsmp.DropSMP;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class magicAxe implements Listener {

    private final HashMap<UUID, Long> magicAxeColdown;

    public magicAxe() {
        this.magicAxeColdown = new HashMap<>();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if (attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.magic-axe.name"))) {
            if (!this.magicAxeColdown.containsKey(attacker.getUniqueId())) {
                //code
                this.magicAxeColdown.put(attacker.getUniqueId(), System.currentTimeMillis());
                victim.getWorld().strikeLightning(victim.getLocation());
            } else {
                long time = System.currentTimeMillis() - magicAxeColdown.get(attacker.getUniqueId());
                if (time >= DropSMP.getPlugin().getConfig().getInt("magic-items.magic-axe.cooldown") * 1000) {
                    //code
                    this.magicAxeColdown.put(attacker.getUniqueId(), System.currentTimeMillis());
                    victim.getWorld().strikeLightning(victim.getLocation());
                }
            }
        }
    }

}
