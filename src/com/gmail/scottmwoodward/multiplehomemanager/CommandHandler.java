package com.gmail.scottmwoodward.multiplehomemanager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor{
	private CommandHome homecommand;
	private CommandSetHome sethomecommand;
	private CommandListHome listhomecommand;
	
	public CommandHandler(MultipleHomeManager plugin){
		this.homecommand = new CommandHome(plugin);
		this.sethomecommand = new CommandSetHome(plugin);
		this.listhomecommand = new CommandListHome(plugin);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("home")){
			return homecommand.execute(sender, args);
		}
		else if(cmd.getName().equalsIgnoreCase("sethome")){
			return sethomecommand.execute(sender, args);
		}
		else if(cmd.getName().equalsIgnoreCase("listhome")){
			return listhomecommand.execute(sender,args);
		}
		return false;
	}

}
