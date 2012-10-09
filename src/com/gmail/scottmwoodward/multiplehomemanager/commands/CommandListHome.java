package com.gmail.scottmwoodward.multiplehomemanager.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.multiplehomemanager.MultipleHomeManager;

public class CommandListHome {

    private MultipleHomeManager plugin;

    public CommandListHome(MultipleHomeManager plugin){
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Your homes are:");
            player.sendMessage(plugin.getDBHandler().listHomes(player.getDisplayName()).split("\n"));
        }
        else{
            sender.sendMessage("You must be logged in, as a player, to list your homes");
        }
        return true;
    }
}