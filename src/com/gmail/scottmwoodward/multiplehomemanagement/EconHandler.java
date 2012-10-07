package com.gmail.scottmwoodward.multiplehomemanagement;

import org.bukkit.entity.Player;

public class EconHandler {
	
	private MultipleHomeManagement plugin;
	
	public EconHandler(MultipleHomeManagement plugin){
		this.plugin = plugin;
	}
	
	public boolean checkBalance(Player player, double cost){
		if(plugin.econ.getBalance(player.getDisplayName())>cost){
			return true;
		}
		return false;
	}
	
	public String takePayment(Player player, double cost){
		plugin.econ.withdrawPlayer(player.getDisplayName(), cost);
		if(cost != 1){
			return String.valueOf(cost)+" "+plugin.econ.currencyNamePlural()+" has been spent for the command: ";
		}
		else{
			return String.valueOf(cost)+" "+plugin.econ.currencyNameSingular()+" has been spent for the command: ";
		}
		
	}

}