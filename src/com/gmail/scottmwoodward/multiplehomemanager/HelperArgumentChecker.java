package com.gmail.scottmwoodward.multiplehomemanager;

import org.bukkit.entity.Player;

public class HelperArgumentChecker {
	
	public int check(int maxArgs, String[] args, Player player, String error){
		if(args.length!=maxArgs){
			player.sendMessage(error);
			return -1;
		}
		try{
			int home = Integer.parseInt(args[0]);
			if(home<1){
				player.sendMessage(error);
				return -1;
			}
			else{
				return home;
			}
		} catch(Exception e){
			player.sendMessage(error);
			return -1;
		}
	}
}
