package com.SirBlobman.blobcatraz;

import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.CommandAFK;
import com.SirBlobman.blobcatraz.command.CommandBalance;
import com.SirBlobman.blobcatraz.command.CommandBan;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.command.CommandChat;
import com.SirBlobman.blobcatraz.command.CommandEconomy;
import com.SirBlobman.blobcatraz.command.CommandFindOrigin;
import com.SirBlobman.blobcatraz.command.CommandFly;
import com.SirBlobman.blobcatraz.command.CommandFreeze;
import com.SirBlobman.blobcatraz.command.CommandHeal;
import com.SirBlobman.blobcatraz.command.CommandItem;
import com.SirBlobman.blobcatraz.command.CommandItemEditor;
import com.SirBlobman.blobcatraz.command.CommandMOTD;
import com.SirBlobman.blobcatraz.command.CommandPortal;
import com.SirBlobman.blobcatraz.command.CommandRandom;
import com.SirBlobman.blobcatraz.command.CommandRandomTP;
import com.SirBlobman.blobcatraz.command.CommandSpawn;
import com.SirBlobman.blobcatraz.command.CommandVote;
import com.SirBlobman.blobcatraz.command.CommandWorth;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.enchant.Cure;
import com.SirBlobman.blobcatraz.enchant.Ender;
import com.SirBlobman.blobcatraz.enchant.Fireball;
import com.SirBlobman.blobcatraz.enchant.Glow;
import com.SirBlobman.blobcatraz.enchant.InstaKill;
import com.SirBlobman.blobcatraz.enchant.Levitate;
import com.SirBlobman.blobcatraz.enchant.Wither;
import com.SirBlobman.blobcatraz.enchant.XPDrain;
import com.SirBlobman.blobcatraz.gui.GuiRandomTP;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.PortalWand;
import com.SirBlobman.blobcatraz.item.Recipes;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;
import com.SirBlobman.blobcatraz.listeners.AFK;
import com.SirBlobman.blobcatraz.listeners.Chat;
import com.SirBlobman.blobcatraz.listeners.Freeze;
import com.SirBlobman.blobcatraz.listeners.GiantDropsPrize;
import com.SirBlobman.blobcatraz.listeners.InvincibleSlimes;
import com.SirBlobman.blobcatraz.listeners.JoinLeave;
import com.SirBlobman.blobcatraz.listeners.MOTD;
import com.SirBlobman.blobcatraz.listeners.Portal;
import com.SirBlobman.blobcatraz.listeners.PreLogin;
import com.SirBlobman.blobcatraz.listeners.Protection;
import com.SirBlobman.blobcatraz.listeners.ShopSigns;
import com.SirBlobman.blobcatraz.listeners.Vote;
import com.SirBlobman.blobcatraz.world.generator.FlatChunkGenerator;

public class Blobcatraz extends JavaPlugin 
{
/*
 * Author = SirBlobman
 * I see you Mr. Plugin Stealer
 * I know that you see my code
 * I wish you the best in understanding it
 * Just credit me if you do use it, I like to help people out
 */
	public static Blobcatraz instance;
	
	@Override
	public void onEnable()
	{
	//Instance
		instance = this;
		
	//Configuration
		if(!getDataFolder().exists()) try{getDataFolder().mkdir();} catch (Exception ex) {Util.print("Could not create the data folder! Disabling Blobcatraz"); Bukkit.getServer().getPluginManager().disablePlugin(this); return;}
		ConfigBlobcatraz.loadConfig();
		ConfigDatabase.loadDatabase();
		ConfigPortals.loadPortals();
		ConfigShop.loadPrices();
		ConfigSpawn.loadSpawn();
		
	//Listeners
		Util.regEvent(new PreLogin());
		Util.regEvent(new JoinLeave());
		
		Util.regEvent(new AFK());
		Util.regEvent(new Chat());
		Util.regEvent(new Freeze());
		Util.regEvent(new GuiRandomTP());
		Util.regEvent(new MOTD());
		Util.regEvent(new ShopSigns());
		
	//Config Listeners
		if(ConfigBlobcatraz.config.getBoolean("protection.preventPrisonEscape")) Util.regEvent(new Protection());
		if(ConfigBlobcatraz.config.getBoolean("random.invincibleSlimes")) Util.regEvent(new InvincibleSlimes());
		if(ConfigBlobcatraz.config.getBoolean("random.giantDropsPrize.enabled")) Util.regEvent(new GiantDropsPrize());
		if(ConfigBlobcatraz.config.getBoolean("random.portals"))
		{
			Util.regEvent(new PortalWand());
			Util.regEvent(new Portal());
		}
		if(ConfigBlobcatraz.config.getBoolean("random.customEnchants"))
		{
			Util.regEvent(new Cure());
			Util.regEvent(new Ender());
			Util.regEvent(new Fireball());
			Util.regEvent(new Glow());
			Util.regEvent(new InstaKill());
			Util.regEvent(new Levitate());
			Util.regEvent(new Wither());
			Util.regEvent(new XPDrain());
		}
		if(ConfigBlobcatraz.config.getBoolean("random.customItems"))
		{
			Util.regEvent(new LightningRod());
			Util.regEvent(new SonicScrewdriver());
			Recipes.loadRecipes();
		}
	//Depend-Based Listeners
		if(getServer().getPluginManager().isPluginEnabled("Votifier")) Util.regEvent(new Vote());
	//Commands
		getCommand("addlore").setExecutor(new CommandItemEditor());
		getCommand("afk").setExecutor(new CommandAFK());
		getCommand("blobcatraz").setExecutor(new CommandBlobcatraz());
		getCommand("balance").setExecutor(new CommandBalance());
		getCommand("ban").setExecutor(new CommandBan());
		getCommand("chat").setExecutor(new CommandChat());
		getCommand("economy").setExecutor(new CommandEconomy());
		getCommand("findorigin").setExecutor(new CommandFindOrigin());
		getCommand("freeze").setExecutor(new CommandFreeze());
		getCommand("fly").setExecutor(new CommandFly());
		getCommand("heal").setExecutor(new CommandHeal());
		getCommand("item").setExecutor(new CommandItem());
		getCommand("item").setTabCompleter(new CommandItem());
		getCommand("portal").setExecutor(new CommandPortal());
		getCommand("random").setExecutor(new CommandRandom());
		getCommand("randomtp").setExecutor(new CommandRandomTP());
		getCommand("removelore").setExecutor(new CommandItemEditor());
		getCommand("rename").setExecutor(new CommandItemEditor());
		getCommand("resetitem").setExecutor(new CommandItemEditor());
		getCommand("repair").setExecutor(new CommandItemEditor());
		getCommand("setlore").setExecutor(new CommandItemEditor());
		getCommand("setmotd").setExecutor(new CommandMOTD());
		getCommand("setspawn").setExecutor(new CommandSpawn());
		getCommand("setworth").setExecutor(new CommandWorth());
		getCommand("spawn").setExecutor(new CommandSpawn());
		getCommand("tempban").setExecutor(new CommandBan());
		getCommand("unban").setExecutor(new CommandBan());
		getCommand("unfreeze").setExecutor(new CommandFreeze());
		getCommand("vote").setExecutor(new CommandVote());
		getCommand("worth").setExecutor(new CommandWorth());
	//Other
		Util.broadcast(Util.pluginEnabled);
	}
	
	@Override
	public void onDisable() 
	{
		Recipes.unloadRecipes();
		Util.broadcast(Util.pluginDisabled);
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String world, String id)
	{
		return new FlatChunkGenerator(id);
	}
}