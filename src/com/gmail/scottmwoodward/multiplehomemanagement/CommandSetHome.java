package com.gmail.scottmwoodward.multiplehomemanagement;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome {

	private MultipleHomeManagement plugin;
	private HelperArgumentChecker checker;
	private String perm;

	public CommandSetHome(MultipleHomeManagement plugin){
		this.plugin = plugin;
		this.checker = new HelperArgumentChecker();
	}

	public boolean execute(CommandSender sender, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;
			int homeNumber = checker.check(1, args, player, "Usage: /sethome <home number>" );
			if(homeNumber<1){
				return true;
			}			
			perm = getPerm(homeNumber);

			if(player.hasPermission(perm)){
				if(plugin.getSetHomeCharge()){
					if(plugin.getUseEcon()){
						if(!plugin.getEconHandler().checkBalance(player, plugin.getSetHomeCost())){
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
				player.sendMessage("You do not have permission to set home number "+args[0]);
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
		return "multihomes.sethome.tier"+String.valueOf((int)Math.ceil(tier));
	}
}