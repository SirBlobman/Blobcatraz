package com.SirBlobman.blobcatraz;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import com.SirBlobman.blobcatraz.command.CommandBalance;
import com.SirBlobman.blobcatraz.command.CommandEconomy;
import com.SirBlobman.blobcatraz.compat.vault.BlobcatrazEconomy;
import com.SirBlobman.blobcatraz.utility.Util;

import net.milkbowl.vault.Vault;

public class Economy extends Blobcatraz
{
	private static final Server S = Bukkit.getServer();
	private static final PluginManager PM = S.getPluginManager();
	private static final ServicesManager SM = S.getServicesManager();
	BlobcatrazEconomy be = new BlobcatrazEconomy();
	
	@Override
	public void onEnable()
	{
		if(PM.isPluginEnabled("Vault"))
		{
			Vault v = getPlugin(Vault.class);
			ServicePriority sp = ServicePriority.Highest;
			SM.register(net.milkbowl.vault.economy.Economy.class, be, v, sp);
		}
		commands();
		Util.broadcast("&2Economy addon enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&2Economy addon disabled");
		if(PM.isPluginEnabled("Vault")) SM.unregister(net.milkbowl.vault.economy.Economy.class, be);
	}
	
	private void commands()
	{
		c("economy", new CommandEconomy(), null);
		c("balance", new CommandBalance(), null);
		c("baltop", new CommandBalance(), null);
		c("withdraw", new CommandBalance(), null);
	}
}