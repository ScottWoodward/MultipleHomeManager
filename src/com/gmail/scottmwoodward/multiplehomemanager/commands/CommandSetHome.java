package com.gmail.scottmwoodward.multiplehomemanager.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.multiplehomemanager.HelperArgumentChecker;
import com.gmail.scottmwoodward.multiplehomemanager.MultipleHomeManager;

public class CommandSetHome {

    private MultipleHomeManager plugin;
    private String perm;

    public CommandSetHome(MultipleHomeManager plugin){
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            int homeNumber = HelperArgumentChecker.check(1, args, player, "Usage: /sethome <home number>" );
            if(homeNumber<1){
                return true;
            }			
            perm = getPerm(homeNumber);

            if(player.hasPermission(perm)){
                if(plugin.getSetHomeCharge()){
                    if(plugin.getUseEcon()){
                        if(!plugin.getEconHandler().hasEnoughMoney(player, plugin.getSetHomeCost())){
                            player.sendMessage("You do not have enough money to set a home");
                            return true;
                        }
                        else{
                            player.sendMessage(plugin.getEconHandler().takePayment(player, plugin.getSetHomeCost())+"/sethome");
                        }
                    }
                }
                if(homeExists(player.getName(),homeNumber)){
                    plugin.getDBHandler().update(player.getName(), player.getWorld().getName(), homeNumber,player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                }
                else{
                    plugin.getDBHandler().insert(player.getName(), player.getWorld().getName(), homeNumber,player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                }
                player.sendMessage("Home "+String.valueOf(homeNumber)+" set.");
            }
            else{
                player.sendMessage("You do not have permission to set home "+args[0]);
            }
            return true;
        }
        else{
            sender.sendMessage("You must be logged in, as a player, to set a home");
            return true;
        }
    }

    private boolean homeExists(String name, int homeNumber){
        return plugin.getDBHandler().rowExist(name, homeNumber);
    }

    private String getPerm(int homeNumber){
        double tier = (double)homeNumber/(double)plugin.getTelePerTier();
        return "mhm.command.sethome.tier"+String.valueOf((int)Math.ceil(tier));
    }
}