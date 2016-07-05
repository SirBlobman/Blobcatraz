package com.SirBlobman.blobcatraz;

import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.*;
import com.SirBlobman.blobcatraz.config.*;
import com.SirBlobman.blobcatraz.enchant.*;
import com.SirBlobman.blobcatraz.gui.RandomTPGui;
import com.SirBlobman.blobcatraz.item.*;
import com.SirBlobman.blobcatraz.listeners.*;

//Author = SirBlobman

public final class Blobcatraz extends JavaPlugin 
{
/*
 * I see you Mr. Plugin Stealer
 * You shouldn't steal code, but you will probably do it anyways
 * Try to understand mine, it isn't difficult
 * xD
 */
	
	public static Blobcatraz instance;
	
	@Override
	public void onEnable()
	{
	//Instance
		instance = this;
		
	//Configs
		ConfigBlobcatraz.loadConfig();
		ConfigPortals.loadPortals();
		ConfigShop.loadPrices();
		ConfigDatabase.loadDatabase();
		
	//Forced Listeners
		Util.regEvent(new PreLogin());
		Util.regEvent(new Join());
		Util.regEvent(new Leave());
		Util.regEvent(new AFK());
		Util.regEvent(new Chat());
		Util.regEvent(new MOTD());
		Util.regEvent(new RandomTPGui());
		
	//Config-defined Listeners
		if(ConfigBlobcatraz.config.getBoolean("random.invincibleSlimes")) Util.regEvent(new InvincibleSlimes());
		if(ConfigBlobcatraz.config.getBoolean("random.giantDropsPrize.enabled")) Util.regEvent(new GiantDropsPrize());
		if(ConfigBlobcatraz.config.getBoolean("random.portals")) Util.regEvent(new Portal());
		if(ConfigBlobcatraz.config.getBoolean("random.customEnchants"))
		{
			Util.regEvent(new Cure());
			Util.regEvent(new Ender());
			Util.regEvent(new Fireball());
			Util.regEvent(new Wither());
			Util.regEvent(new XPDrain());
		}
		if(ConfigBlobcatraz.config.getBoolean("random.customItems"))
		{
			Util.regEvent(new LightningRod());
			Util.regEvent(new SonicScrewdriver());
			Recipes.loadRecipes();
		}
		if(ConfigBlobcatraz.config.getBoolean("protection.preventPrisonEscape")) Util.regEvent(new Protection());
	//Depend Listeners
		if(getServer().getPluginManager().isPluginEnabled("Votifier")) Util.regEvent(new Votes());
		
	//Commands
		getCommand("addlore").setExecutor(new ItemEditors());
		getCommand("afk").setExecutor(new CommandAFK());
		getCommand("blobcatraz").setExecutor(new CommandBlobcatraz());
		getCommand("balance").setExecutor(new CommandBalance());
		getCommand("ban").setExecutor(new CommandBan());
		getCommand("chat").setExecutor(new CommandChat());
		getCommand("economy").setExecutor(new CommandEconomy());
		getCommand("findorigin").setExecutor(new CommandFindOrigin());
		getCommand("fly").setExecutor(new CommandFly());
		getCommand("heal").setExecutor(new CommandHeal());
		getCommand("item").setExecutor(new CommandItem());
		getCommand("item").setTabCompleter(new CommandItem());
		getCommand("portal").setExecutor(new CommandPortal());
		getCommand("random").setExecutor(new CommandRandom());
		getCommand("randomtp").setExecutor(new CommandRandomTP());
		getCommand("rename").setExecutor(new ItemEditors());
		getCommand("resetitem").setExecutor(new ItemEditors());
		getCommand("repair").setExecutor(new ItemEditors());
		getCommand("setlore").setExecutor(new ItemEditors());
		getCommand("setmotd").setExecutor(new CommandSetMOTD());
		getCommand("setworth").setExecutor(new CommandWorth());
		getCommand("tempban").setExecutor(new CommandBan());
		getCommand("unban").setExecutor(new CommandBan());
		getCommand("vote").setExecutor(new CommandVote());
		getCommand("worth").setExecutor(new CommandWorth());
		
		Util.broadcast("This plugin has been §2§lenabled§r!");
	}
	
	@Override
	public void onDisable() {Util.broadcast("This plugin has been §4§ldisabled§r!");}
}