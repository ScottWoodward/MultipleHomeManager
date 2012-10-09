package com.gmail.scottmwoodward.multiplehomemanager.handlers;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.scottmwoodward.multiplehomemanager.MultipleHomeManager;
import com.gmail.scottmwoodward.multiplehomemanager.commands.CommandHome;
import com.gmail.scottmwoodward.multiplehomemanager.commands.CommandListHome;
import com.gmail.scottmwoodward.multiplehomemanager.commands.CommandMHMAdmin;
import com.gmail.scottmwoodward.multiplehomemanager.commands.CommandSetHome;

public class CommandHandler implements CommandExecutor{
    private CommandHome homecommand;
    private CommandSetHome sethomecommand;
    private CommandListHome listhomecommand;
    private CommandMHMAdmin mhmadmincommand;

    public CommandHandler(MultipleHomeManager plugin){
        this.homecommand = new CommandHome(plugin);
        this.sethomecommand = new CommandSetHome(plugin);
        this.listhomecommand = new CommandListHome(plugin);
        this.mhmadmincommand = new CommandMHMAdmin(plugin);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("home")){
            return homecommand.execute(sender, args);
        }
        else if(cmd.getName().equalsIgnoreCase("sethome")){
            return sethomecommand.execute(sender, args);
        }
        else if(cmd.getName().equalsIgnoreCase("homelist")){
            return listhomecommand.execute(sender,args);
        }
        else if(cmd.getName().equalsIgnoreCase("mhmadmin")){
            return  mhmadmincommand.execute(sender,args,listhomecommand);
        }
        return true;
    }

}
