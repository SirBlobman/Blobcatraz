package com.SirBlobman.blobcatraz;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.AFKCommand;
import com.SirBlobman.blobcatraz.command.Balance;
import com.SirBlobman.blobcatraz.command.Ban;
import com.SirBlobman.blobcatraz.command.Chat;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.command.CommandRandom;
import com.SirBlobman.blobcatraz.command.FindOrigin;
import com.SirBlobman.blobcatraz.command.Fly;
import com.SirBlobman.blobcatraz.command.Heal;
import com.SirBlobman.blobcatraz.command.I;
import com.SirBlobman.blobcatraz.command.ItemEditor;
import com.SirBlobman.blobcatraz.command.Portal;
import com.SirBlobman.blobcatraz.command.RandomTP;
import com.SirBlobman.blobcatraz.command.SetMOTD;
import com.SirBlobman.blobcatraz.command.Vote;
import com.SirBlobman.blobcatraz.command.Worth;
import com.SirBlobman.blobcatraz.config.Database;
import com.SirBlobman.blobcatraz.config.Portals;
import com.SirBlobman.blobcatraz.config.Shop;
import com.SirBlobman.blobcatraz.enchant.Cure;
import com.SirBlobman.blobcatraz.enchant.Ender;
import com.SirBlobman.blobcatraz.enchant.Fireball;
import com.SirBlobman.blobcatraz.enchant.Glow;
import com.SirBlobman.blobcatraz.enchant.Levitate;
import com.SirBlobman.blobcatraz.enchant.Wither;
import com.SirBlobman.blobcatraz.enchant.XPDrain;
import com.SirBlobman.blobcatraz.gui.RandomTPGui;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.Recipes;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;
import com.SirBlobman.blobcatraz.listeners.AFK;
import com.SirBlobman.blobcatraz.listeners.BanListener;
import com.SirBlobman.blobcatraz.listeners.ChatMute;
import com.SirBlobman.blobcatraz.listeners.ChatPing;
import com.SirBlobman.blobcatraz.listeners.ChatReplacer;
import com.SirBlobman.blobcatraz.listeners.GiantDropsNotchApple;
import com.SirBlobman.blobcatraz.listeners.InPortal;
import com.SirBlobman.blobcatraz.listeners.JoinBroadcast;
import com.SirBlobman.blobcatraz.listeners.LeaveBroadcast;
import com.SirBlobman.blobcatraz.listeners.PrisonProtection;
import com.SirBlobman.blobcatraz.listeners.SetMotd;
import com.SirBlobman.blobcatraz.listeners.UnkillableSlimes;
import com.SirBlobman.blobcatraz.listeners.Votes;

//String Author = "SirBlobman";

public final class Blobcatraz extends JavaPlugin 
{
	/*I see you Mr.Plugin Stealer
	 * I told you not to steal my code, but you probably will anyways
	 *Try to understand it, its not that difficult
	 * xD
	 * 
	 */
	public static Blobcatraz instance;
	public FileConfiguration config = getConfig();

	@Override
	public void onEnable() 
	{
	//Instance
		instance = this;
		
	//Config
		config.addDefault("protection.prevent-prison-escape", false);
		config.addDefault("chat.disabled", false);
		config.addDefault("chat.ping", false);
		config.addDefault("chat.use_special", false);
		config.addDefault("random.unkillable-slimes", false);
		config.addDefault("random.giant-drops-notch-apple", false);
		config.addDefault("random.custom-items", false);
		config.addDefault("random.custom-enchants", false);
		config.addDefault("random.portals", false);
		config.addDefault("randomtp.maxFarDistance", 6000);
		config.addDefault("randomtp.maxNormalDistance", 3000);
		config.addDefault("randomtp.maxTinyDistance", 1000);
		List<String> default_enabled_worlds = Arrays.asList("world", "world_nether", "world_the_end");
		config.addDefault("randomtp.enabledWorlds", default_enabled_worlds);
		List<String> default_vote_links = Arrays.asList("Link 1", "Link 2", "Link 3");
		config.addDefault("vote.links", default_vote_links);
		config.addDefault("motd", Util.blobcatraz +  "This is the default MOTD");
		config.options().copyDefaults(true);
		saveConfig();
		Portals.portalConfig.options().copyDefaults(true);
		Portals.loadPortals();
		Portals.savePortals();
		Shop.loadPrices();
		Database.databaseConfig.options().copyDefaults(true);
		Database.saveDatabase();
		Database.loadDatabase();
		
	//Listeners
		Util.regEvent(new BanListener());
		Util.regEvent(new JoinBroadcast());
		Util.regEvent(new LeaveBroadcast());
		Util.regEvent(new AFK());
		Util.regEvent(new ChatMute());
		Util.regEvent(new SetMotd());
		Util.regEvent(new RandomTPGui());
		
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
			Util.regEvent(new Fireball());
			Util.regEvent(new Wither());
			Util.regEvent(new Levitate());
			Util.regEvent(new Glow());
			Util.regEvent(new XPDrain());
			Util.regEvent(new Cure());
			Util.regEvent(new Ender());
		}
		if(config.getBoolean("random.custom-items") == true)
		{
			Util.regEvent(new LightningRod());
			Util.regEvent(new SonicScrewdriver());
			Recipes.loadRecipes();
		}
		if(config.getBoolean("random.portals") == true)
		{
			Util.regEvent(new InPortal());
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
		getCommand("balance").setExecutor(new Balance());
		getCommand("ban").setExecutor(new Ban());
		getCommand("chat").setExecutor(new Chat());
		getCommand("economy").setExecutor(new com.SirBlobman.blobcatraz.command.Economy());
		getCommand("findorigin").setExecutor(new FindOrigin());
		getCommand("fly").setExecutor(new Fly());
		getCommand("heal").setExecutor(new Heal());
		getCommand("item").setExecutor(new I());
		getCommand("item").setTabCompleter(new I());
		getCommand("portal").setExecutor(new Portal());
		getCommand("random").setExecutor(new CommandRandom());
		getCommand("randomtp").setExecutor(new RandomTP());
		getCommand("rename").setExecutor(new ItemEditor());
		getCommand("resetitem").setExecutor(new ItemEditor());
		getCommand("repair").setExecutor(new ItemEditor());
		getCommand("setlore").setExecutor(new ItemEditor());
		getCommand("setmotd").setExecutor(new SetMOTD());
		getCommand("setworth").setExecutor(new Worth());
		getCommand("tempban").setExecutor(new Ban());
		getCommand("unban").setExecutor(new Ban());
		getCommand("vote").setExecutor(new Vote());
		getCommand("worth").setExecutor(new Worth());
		
		Util.broadcast("This plugin has been §2§lenabled§r!");	
	}

	@Override
	public void onDisable() 
	{
		Util.broadcast("This plugin has been §4§ldisabled§r!");
	}
}