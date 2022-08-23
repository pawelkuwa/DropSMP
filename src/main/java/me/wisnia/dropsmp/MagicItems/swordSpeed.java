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

public class swordSpeed implements Listener {

    private final HashMap<UUID, Long> speedCooldown;

    public swordSpeed() {
        this.speedCooldown = new HashMap<>();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if (attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DropSMP.getPlugin().getConfig().getString("magic-items.sword-speed.name"))) {
            if (!this.speedCooldown.containsKey(attacker.getUniqueId())) {
                //code
                this.speedCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
            } else {
                long time = System.currentTimeMillis() - speedCooldown.get(attacker.getUniqueId());
                if (time >= DropSMP.getPlugin().getConfig().getInt("magic-items.sword-speed.cooldown") * 1000) {
                    //code
                    this.speedCooldown.put(attacker.getUniqueId(), System.currentTimeMillis());
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
                }
            }
        }
    }

}
