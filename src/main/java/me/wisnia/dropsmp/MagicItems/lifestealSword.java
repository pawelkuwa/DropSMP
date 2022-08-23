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

public class lifestealSword implements Listener {

    private final HashMap<UUID, Long> lifestealCooldown;

    public lifestealSword() {
        this.lifestealCooldown = new HashMap<>();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if (attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.lifesteal-sword.name"))) {
            if (!this.lifestealCooldown.containsKey(attacker.getUniqueId())) {
                //code
                this.lifestealCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                Double health = attacker.getHealth() + 0.2;
                attacker.setHealth(health);
            } else {
                long time = System.currentTimeMillis() - lifestealCooldown.get(attacker.getUniqueId());
                if (time >= DropSMP.getPlugin().getConfig().getInt("magic-items.lifesteal-sword.cooldown") * 1000) {
                    //code
                    this.lifestealCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                    Double health = attacker.getHealth() + 0.2;
                    attacker.setHealth(health);
                }
            }
        }
    }

}
