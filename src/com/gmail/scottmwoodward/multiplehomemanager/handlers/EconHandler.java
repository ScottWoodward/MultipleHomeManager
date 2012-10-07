package com.gmail.scottmwoodward.multiplehomemanager.handlers;

import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.multiplehomemanager.MultipleHomeManager;

public class EconHandler {
	
	private MultipleHomeManager plugin;
	
	public EconHandler(MultipleHomeManager plugin){
		this.plugin = plugin;
	}
	
	public boolean hasEnoughMoney(Player player, double cost){
		if(plugin.econ.getBalance(player.getDisplayName())>=cost){
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
