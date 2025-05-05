package com.mills.core.commands.teleport.warps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WarpTabComplete implements TabCompleter {

    private final WarpManager warpManager;

    public WarpTabComplete(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (command.getName().equalsIgnoreCase("warp") && args.length == 1) {
            Set<String> allWarps = warpManager.getAllWarps();
            List<String> completions = new ArrayList<>();
            String input = args[0].toLowerCase();
            for (String warp : allWarps) {
                if (warp.toLowerCase().startsWith(input)) {
                    completions.add(warp);
                }
            }
            return completions;
        }

        return Collections.emptyList();
    }

}
