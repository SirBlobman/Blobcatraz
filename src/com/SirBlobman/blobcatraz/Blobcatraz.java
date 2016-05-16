package com.SirBlobman.blobcatraz;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.AFKCommand;
import com.SirBlobman.blobcatraz.command.Chat;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.command.CommandRandom;
import com.SirBlobman.blobcatraz.command.FindOrigin;
import com.SirBlobman.blobcatraz.command.ItemEditor;
import com.SirBlobman.blobcatraz.command.Portal;
import com.SirBlobman.blobcatraz.command.SetMOTD;
import com.SirBlobman.blobcatraz.command.Vote;
import com.SirBlobman.blobcatraz.enchant.FireballEnchant;
import com.SirBlobman.blobcatraz.enchant.GlowEnchant;
import com.SirBlobman.blobcatraz.enchant.LevitateEnchant;
import com.SirBlobman.blobcatraz.enchant.VillagerFixEnchant;
import com.SirBlobman.blobcatraz.enchant.WitherEnchant;
import com.SirBlobman.blobcatraz.enchant.XPStealEnchant;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.Recipes;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;
import com.SirBlobman.blobcatraz.listeners.AFK;
import com.SirBlobman.blobcatraz.listeners.ChatMute;
import com.SirBlobman.blobcatraz.listeners.ChatPing;
import com.SirBlobman.blobcatraz.listeners.ChatReplacer;
import com.SirBlobman.blobcatraz.listeners.GiantDropsNotchApple;
import com.SirBlobman.blobcatraz.listeners.JoinBroadcast;
import com.SirBlobman.blobcatraz.listeners.LeaveBroadcast;
import com.SirBlobman.blobcatraz.listeners.PrisonProtection;
import com.SirBlobman.blobcatraz.listeners.SetMotd;
import com.SirBlobman.blobcatraz.listeners.UnkillableSlimes;
import com.SirBlobman.blobcatraz.listeners.Votes;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

//String Author = "SirBlobman";

public final class Blobcatraz extends JavaPlugin implements Listener 
{
	//I see you Mr.Plugin Stealer
	//I told you not to steal my code, but you probably will anyways
	//Try to understand it, its not that difficult
	//xD
	public static Permission perms = null;
	public static Economy econ = null;
	
	public static Blobcatraz instance;
	public FileConfiguration config = getConfig();

	@Override
	public void onEnable() 
	{
	//Instance
		instance = this;
		
	//Config
		setupPerms();
		setupEconomy();
		config.addDefault("chat.disabled", false);
		config.addDefault("chat.ping", true);
		config.addDefault("chat.use_special", true);
		config.addDefault("protection.prevent-prison-escape", true);
		config.addDefault("random.unkillable-slimes", true);
		config.addDefault("random.giant-drops-notch-apple", true);
		config.addDefault("motd", "§1[§6Blobcatraz§1]§r This is the default MOTD");
		config.addDefault("random.custom-items", true);
		config.addDefault("random.custom-enchants", true);	
		config.options().copyDefaults(true);
		saveConfig();
		Portal.savePortalConfig();
		Portal.getPortalConfig().options().copyDefaults(true);
		Portal.savePortals();
		
	//Listeners
		Util.regEvent(new JoinBroadcast());
		Util.regEvent(new LeaveBroadcast());
		Util.regEvent(new AFK());
		Util.regEvent(new ChatMute());
		Util.regEvent(new SetMotd());
		
	//Config Defined Listeners
		if (config.getBoolean("random.unkillable-slimes") == true) 
		{
			Util.regEvent(new UnkillableSlimes());
		}
		if (config.getBoolean("random.giant-drops-notch-apple") == true) 
		{
			Util.regEvent(new GiantDropsNotchApple());
		}
		if (config.getBoolean("protection.prevent-prison-escape") == true) 
		{
			Util.regEvent(new PrisonProtection());
		}
		if(config.getBoolean("chat.use_special") == true)
		{
			Util.regEvent(new ChatReplacer());
		}
		if(config.getBoolean("chat.ping") == true)
		{
			Util.regEvent(new ChatPing());
		}
		if(config.getBoolean("random.custom-enchants") == true)
		{
			Util.regEvent(new FireballEnchant());
			Util.regEvent(new WitherEnchant());
			Util.regEvent(new LevitateEnchant());
			Util.regEvent(new GlowEnchant());
			Util.regEvent(new XPStealEnchant());
			Util.regEvent(new VillagerFixEnchant());
		}
		if(config.getBoolean("random.custom-items") == true)
		{
			Util.regEvent(new LightningRod());
			Util.regEvent(new SonicScrewdriver());
			Recipes.loadRecipes();
		}
	//Depend Based Listeners
		if(getServer().getPluginManager().isPluginEnabled("Votifier"))
		{
			Util.regEvent(new Votes());
		}
		
	//Commands
		getCommand("addlore").setExecutor(new ItemEditor());
		getCommand("afk").setExecutor(new AFKCommand());
		getCommand("blobcatraz").setExecutor(new CommandBlobcatraz());
		getCommand("chat").setExecutor(new Chat());
		getCommand("findorigin").setExecutor(new FindOrigin());
		getCommand("portal").setExecutor(new Portal());
		getCommand("random").setExecutor(new CommandRandom());
		getCommand("rename").setExecutor(new ItemEditor());
		getCommand("resetitem").setExecutor(new ItemEditor());
		getCommand("repair").setExecutor(new ItemEditor());
		getCommand("setlore").setExecutor(new ItemEditor());
		getCommand("setmotd").setExecutor(new SetMOTD());
		getCommand("vote").setExecutor(new Vote());
		
		Util.broadcast("§1[§6Blobcatraz§1]§r This plugin has been §2§lenabled§r!");	
	}

	@Override
	public void onDisable() 
	{
		Util.broadcast("§1[§6Blobcatraz§1]§r This plugin has been §4§ldisabled§r!");
	}
	
	private boolean setupPerms()
	{
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		if(rsp != null)
		{
			perms = (Permission)rsp.getProvider();
		}
		return perms != null;
	}
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp != null)
		{
			econ = (Economy)rsp.getProvider();
		}
		return econ != null;
	}
}