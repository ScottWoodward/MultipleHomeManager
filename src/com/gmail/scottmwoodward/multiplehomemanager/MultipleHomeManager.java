package com.gmail.scottmwoodward.multiplehomemanager;


import java.util.LinkedList;
import java.util.Queue;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.scottmwoodward.multiplehomemanager.handlers.DBHandler;
import com.gmail.scottmwoodward.multiplehomemanager.handlers.EconHandler;

public class MultipleHomeManager extends JavaPlugin{
	private CommandHandler handler;
	private DBHandler dbHandler;
	private EconHandler econHandler;
	private int delay;
	private int telePerTier;
	public Economy econ;
	private boolean useEcon;
	private boolean setHomeCharge;
	private boolean homeCharge;
	private double setHomeCost;
	private double homeCost;
	private Queue<String> pending;


	@Override
	public void onEnable(){
		pending = new LinkedList<String>();
		dbHandler = new DBHandler(this);
		dbHandler.sqlConnection();
		dbHandler.sqlTableCheck();
		econHandler = new EconHandler(this);
		handler = new CommandHandler(this);
		this.saveDefaultConfig();
		telePerTier = this.getConfig().getInt("HomesPerTier");
		delay = this.getConfig().getInt("TeleportDelay");
		getCommand("home").setExecutor(handler);
		getCommand("sethome").setExecutor(handler);
		getCommand("listhome").setExecutor(handler);
		useEcon = this.getConfig().getBoolean("EconomyEnabled");
		if(useEcon){
			if(!setupEconomy()){
				this.getLogger().warning("Vault missing, economy functions disabled");
				useEcon = false;
			}
		}
		if(useEcon){
			setHomeCharge = this.getConfig().getBoolean("SetHomeCharge");
			homeCharge = this.getConfig().getBoolean("HomeCharge");
			setHomeCost = this.getConfig().getDouble("SetHomeCost");
			homeCost = this.getConfig().getDouble("HomeCost");
		}
		//TODO Implement getCommand("listhome").setExecutor(handler);
	}

	@Override
	public void onDisable(){
		dbHandler.sqlClose();
		dbHandler = null;
	}

	public DBHandler getDBHandler(){
		return this.dbHandler;		
	}

	public EconHandler getEconHandler(){
		return this.econHandler;		
	}

	public int getDelay(){
		return delay;
	}

	public int getTelePerTier(){
		return telePerTier;
	}

	public boolean getUseEcon(){
		return useEcon;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public boolean getSetHomeCharge(){
		return setHomeCharge;
	}

	public boolean getHomeCharge(){
		return homeCharge;
	}

	public double getSetHomeCost(){
		return setHomeCost;
	}

	public double getHomeCost(){
		return homeCost;
	}

	public Queue<String> getPending(){
		return pending;
	}

}
