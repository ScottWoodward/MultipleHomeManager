package com.gmail.scottmwoodward.multiplehomemanager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RunnableTeleport implements Runnable{
	private double x;
	private double y;
	private double z;
	private World world;
	private Player player;
	private Location loc;
	private MultipleHomeManager plugin;

	public RunnableTeleport(Player player, World world, double x, double y, double z, MultipleHomeManager plugin){
		this.plugin = plugin;
		this.player = player;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.loc = player.getLocation();
		player.sendMessage("Pending teleport, please stand still for "+String.valueOf(plugin.getDelay()) +" seconds.");
	}

	public void run(){
		if(plugin.getUseEcon()&&plugin.getHomeCharge()){
			if(!plugin.getEconHandler().hasEnoughMoney(player, plugin.getHomeCost())){
				plugin.getPending().remove(player.getDisplayName());
				player.sendMessage("You do not have enough money to teleport to a home");
				return;
			}
		}
		if(loc.distance(player.getLocation()) <=1 ){
			if(plugin.getUseEcon()&&plugin.getHomeCharge()){
				player.sendMessage(plugin.getEconHandler().takePayment(player, plugin.getHomeCost())+"/home");
			}
			player.teleport(new Location(world, x, y, z));
			player.sendMessage("Teleported!");
		}
		else{
			player.sendMessage("Moved, cancelling teleport");
		}
		plugin.getPending().remove(player.getDisplayName());
	}

}
