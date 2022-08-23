package me.wisnia.dropsmp.MagicItems;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class tribalChest implements Listener {

    /* soon */
    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) {
        e.getPlayer().sendMessage("test");
    }

}
