package com.gmail.scottmwoodward.multiplehomemanagement;

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
	private MultipleHomeManagement plugin;
	
	public RunnableTeleport(Player player, World world, double x, double y, double z, MultipleHomeManagement plugin){
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
		if(!plugin.getEconHandler().checkBalance(player, plugin.getHomeCost())){
			plugin.getPending().remove(player.getDisplayName());
			return;
		}
		if(loc.distance(player.getLocation()) <=1 ){
			player.sendMessage(plugin.getEconHandler().takePayment(player, plugin.getHomeCost())+"/home");
			player.teleport(new Location(world, x, y, z));
			player.sendMessage("Teleported!");
		}
		else{
			player.sendMessage("Moved, cancelling teleport");
		}
		plugin.getPending().remove(player.getDisplayName());
	}

}
