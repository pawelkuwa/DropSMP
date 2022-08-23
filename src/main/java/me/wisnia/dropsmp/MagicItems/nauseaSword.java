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

public class nauseaSword implements Listener {

    private final HashMap<UUID, Long> nauseaCooldown;

    public nauseaSword() {
        this.nauseaCooldown = new HashMap<>();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if (attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.nausea-sword.name"))) {
            if (!this.nauseaCooldown.containsKey(attacker.getUniqueId())) {
                //code
                this.nauseaCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
            } else {
                long time = System.currentTimeMillis() - nauseaCooldown.get(attacker.getUniqueId());
                if (time >= DropSMP.getPlugin().getConfig().getInt("magic-items.nausea-sword.cooldown") * 1000) {
                    //code
                    this.nauseaCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                }
            }
        }
    }

}
