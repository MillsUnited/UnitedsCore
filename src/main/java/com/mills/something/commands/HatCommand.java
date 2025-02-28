package com.mills.something.commands;

import com.mills.something.Main;
import com.mills.something.Utills;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class HatCommand implements CommandExecutor {

    private Main main;

    public HatCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemStack helmet = player.getInventory().getHelmet();
            String itemname = Utills.format(item.getType());

            if (item != null) {

                if (helmet == null) {

                    player.getInventory().setHelmet(item);
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("hat.set-helmet").replace("<item>", itemname));
                    player.getInventory().remove(item);

                } else if (helmet != null) {

                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("hat.has-helmet"));

                }

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("hat.invalid-item"));
            }


        }


        return false;
    }
}
