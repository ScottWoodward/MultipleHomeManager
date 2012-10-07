package com.gmail.scottmwoodward.multiplehomemanager;


import org.bukkit.World;
import org.bukkit.entity.Player;

public class HelperTeleport {
	private MultipleHomeManager plugin;

	public HelperTeleport(MultipleHomeManager plugin){
		this.plugin = plugin;

	}

	public void teleport(Player player, int homeNumber){
		if(homeExists(player.getName(),homeNumber)){
			double x = plugin.getDBHandler().getComponent(player.getName(), homeNumber, "x");
			double y = plugin.getDBHandler().getComponent(player.getName(), homeNumber, "y");
			double z = plugin.getDBHandler().getComponent(player.getName(), homeNumber, "z");
			if((x==0)||(y==0)||(z==0)){
				player.sendMessage("Command 'Home' Failed");
			}
			World world = plugin.getServer().getWorld(plugin.getDBHandler().getWorld(player.getName(), homeNumber));
			plugin.getPending().add(player.getDisplayName());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RunnableTeleport(player, world, x, y, z, plugin), convertDelay());
		}
		else{
			player.sendMessage("You have not set Home number "+String.valueOf(homeNumber));
		}
		
	}

	private boolean homeExists(String name, int homeNumber){
		return plugin.getDBHandler().rowExist(name, homeNumber);
	}
	
	private long convertDelay(){
		return (long)(plugin.getDelay()*20);
	}

}
