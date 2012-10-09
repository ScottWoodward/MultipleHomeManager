package com.gmail.scottmwoodward.multiplehomemanager.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.multiplehomemanager.MultipleHomeManager;

public class CommandMHMAdmin {

    private MultipleHomeManager plugin;

    public CommandMHMAdmin(MultipleHomeManager plugin){
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args, CommandListHome lister){
        if(args.length==0){
            sender.sendMessage("MultipleHomeManager Admin\n======================\n/mhmadmin homelist <player>: Prints a list of the specified player's homes\n/mhmadmin unsethome <player> <home number>: Unsets the chosen player's specified home".split("\n"));
            return true;
        }
        if(args[0].equalsIgnoreCase("homelist")){
            if(sender.hasPermission("mhm.command.mhmadmin.listothers")||sender.hasPermission("mhm.command.mhmadmin.*")){
                if((args.length==2)){
                    sender.sendMessage(args[1]+"'s homes:");
                    sender.sendMessage(plugin.getDBHandler().listHomes(args[1]).split("\n"));
                    return true;
                }
                else{
                    sender.sendMessage("Usage: /mhmadmin homelist <player>");
                    return true;
                }  
            }
            else{
                sender.sendMessage("You do not have permission to use /mhmadmin homelist");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("unsethome")){
            if(sender.hasPermission("mhm.command.mhmadmin.unsethome")||sender.hasPermission("mhm.command.mhmadmin.*")){
                if((args.length==3)){
                    int homeNumber = Integer.parseInt(args[2]);
                    if(homeNumber<1){
                        sender.sendMessage("Usage: /mhmadmin unsethome <player> <home number>");
                        return true;
                    }
                    if(plugin.getDBHandler().rowExist(args[1], homeNumber)){
                        plugin.getDBHandler().unset(args[1],homeNumber);
                        if(sender instanceof Player){
                            sender.sendMessage(args[1]+"'s home has been removed");
                        }
                        plugin.getLogger().info(sender.getName()+" has removed "+args[1]+"'s home "+args[2]);
                        return true;
                    }
                    sender.sendMessage("No such home exists for "+args[1]);
                    return true;
                }
                else{
                    sender.sendMessage("Usage: /mhmadmin unsethome <player> <home number>");
                    return true;
                } 
            }
            else{
                sender.sendMessage("You do not have permission to use /mhmadmin unsethome");
                return true;
            }
        }
        sender.sendMessage("See /mhmadmin for usage details");
        return true;
    }
}
