package me.wisnia.dropsmp.Listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class commandTabComplete implements TabCompleter, Listener {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("dropsmp")) {
            if (args.length == 1) {
                List<String> arguments = new ArrayList<>();
                arguments.add("reload");
                arguments.add("dajprzedmioty");
                arguments.add("edytujdrop");
                return arguments;
            }
        }
        return null;
    }
}
