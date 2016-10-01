package com.SirBlobman.blobcatraz;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.SirBlobman.blobcatraz.command.CommandAFK;
import com.SirBlobman.blobcatraz.command.CommandAutoPickup;
import com.SirBlobman.blobcatraz.command.CommandBalance;
import com.SirBlobman.blobcatraz.command.CommandBan;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.command.CommandChat;
import com.SirBlobman.blobcatraz.command.CommandCommandSpy;
import com.SirBlobman.blobcatraz.command.CommandDisco;
import com.SirBlobman.blobcatraz.command.CommandEconomy;
import com.SirBlobman.blobcatraz.command.CommandEdit;
import com.SirBlobman.blobcatraz.command.CommandEnchant;
import com.SirBlobman.blobcatraz.command.CommandFindOrigin;
import com.SirBlobman.blobcatraz.command.CommandFly;
import com.SirBlobman.blobcatraz.command.CommandFreeze;
import com.SirBlobman.blobcatraz.command.CommandGamemode;
import com.SirBlobman.blobcatraz.command.CommandHeal;
import com.SirBlobman.blobcatraz.command.CommandHome;
import com.SirBlobman.blobcatraz.command.CommandInventory;
import com.SirBlobman.blobcatraz.command.CommandItem;
import com.SirBlobman.blobcatraz.command.CommandKit;
import com.SirBlobman.blobcatraz.command.CommandLag;
import com.SirBlobman.blobcatraz.command.CommandMOTD;
import com.SirBlobman.blobcatraz.command.CommandMessage;
import com.SirBlobman.blobcatraz.command.CommandPortal;
import com.SirBlobman.blobcatraz.command.CommandRandom;
import com.SirBlobman.blobcatraz.command.CommandRandomTP;
import com.SirBlobman.blobcatraz.command.CommandRecipe;
import com.SirBlobman.blobcatraz.command.CommandSpawn;
import com.SirBlobman.blobcatraz.command.CommandTag;
import com.SirBlobman.blobcatraz.command.CommandTeleport;
import com.SirBlobman.blobcatraz.command.CommandTokenShop;
import com.SirBlobman.blobcatraz.command.CommandVanish;
import com.SirBlobman.blobcatraz.command.CommandVote;
import com.SirBlobman.blobcatraz.command.CommandWarp;
import com.SirBlobman.blobcatraz.command.CommandWorth;
import com.SirBlobman.blobcatraz.compat.vault.BlobcatrazEconomy;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigEmojis;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.enchant.Cure;
import com.SirBlobman.blobcatraz.enchant.Ender;
import com.SirBlobman.blobcatraz.enchant.Explosion;
import com.SirBlobman.blobcatraz.enchant.Fireball;
import com.SirBlobman.blobcatraz.enchant.Glow;
import com.SirBlobman.blobcatraz.enchant.InstaKill;
import com.SirBlobman.blobcatraz.enchant.Levitate;
import com.SirBlobman.blobcatraz.enchant.LifeSteal;
import com.SirBlobman.blobcatraz.enchant.Strength;
import com.SirBlobman.blobcatraz.enchant.Wither;
import com.SirBlobman.blobcatraz.enchant.XPDrain;
import com.SirBlobman.blobcatraz.enchant.event.EnchantListener;
import com.SirBlobman.blobcatraz.gui.GuiRandomTP;
import com.SirBlobman.blobcatraz.gui.GuiTokenShop;
import com.SirBlobman.blobcatraz.gui.GuiWarps;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.PortalWand;
import com.SirBlobman.blobcatraz.item.Recipes;
import com.SirBlobman.blobcatraz.item.SandWand;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;
import com.SirBlobman.blobcatraz.listener.AFK;
import com.SirBlobman.blobcatraz.listener.AutoPickup;
import com.SirBlobman.blobcatraz.listener.BanChecker;
import com.SirBlobman.blobcatraz.listener.Chat;
import com.SirBlobman.blobcatraz.listener.CombatLog;
import com.SirBlobman.blobcatraz.listener.CommandSpy;
import com.SirBlobman.blobcatraz.listener.Freeze;
import com.SirBlobman.blobcatraz.listener.GiantLoot;
import com.SirBlobman.blobcatraz.listener.HubEffects;
import com.SirBlobman.blobcatraz.listener.InvincibleSlime;
import com.SirBlobman.blobcatraz.listener.JoinQuit;
import com.SirBlobman.blobcatraz.listener.MOTD;
import com.SirBlobman.blobcatraz.listener.MobMerge;
import com.SirBlobman.blobcatraz.listener.Portal;
import com.SirBlobman.blobcatraz.listener.Protection;
import com.SirBlobman.blobcatraz.listener.Shop;
import com.SirBlobman.blobcatraz.listener.VoteLogger;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

/**
 * I see you Mr. Plugin Stealer
 * <br>I know that you see my code
 * <br>I wish you the best in understanding it
 * <br>Just credit me if you use it!
 * <br>I don't like hard work going to waste
 * <br><br>This is the main class for Blobcatraz
 * @author SirBlobman
 */
public class Blobcatraz extends JavaPlugin 
{
	public static Blobcatraz instance;
	private static YamlConfiguration config;
	public static File folder;
	
	private static PluginManager PM = Bukkit.getPluginManager();
	private static ServicesManager SM = Bukkit.getServicesManager();
	private static BukkitScheduler BS = Bukkit.getScheduler();
	
	@Override
	public void onEnable()
	{
		instance = this;
		configs();
		listeners();
		enchants();
		commands();
		
		Util.broadcast(Util.enabled);
	}
	
	@Override
	public void onDisable()
	{
		try
		{
			PlayerUtil.closeAllInventories();
			Recipes.unload();

			Util.broadcast(Util.disabled);
		} 
		catch(Exception | Error ex) 
		{System.out.println("Test");}
	}
	
	@Override
	public YamlConfiguration getConfig()
	{
		return config;
	}
	
	private void configs()
	{
		folder = getDataFolder();
		if(!folder.exists())
		{
			try{folder.mkdir();} catch(Exception ex)
			{
				System.out.println("Failed to create data folder! Disabling Blobcatraz");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
		}
		config = ConfigBlobcatraz.load();
		for(OfflinePlayer op : Bukkit.getOfflinePlayers()) ConfigDatabase.load(op);
		ConfigEmojis.load();
		ConfigKits.reload();
		ConfigLanguage.load();
		ConfigPortals.load();
		ConfigShop.load();
		ConfigSpawn.load();
		ConfigWarps.reload();
	}
	
	private void listeners()
	{
	//Default
		Util.regEvents(new AFK());
		Util.regEvents(new BanChecker());
		Util.regEvents(new Chat());
		Util.regEvents(new Freeze());
		Util.regEvents(new GuiRandomTP());
		Util.regEvents(new GuiTokenShop());
		Util.regEvents(new GuiWarps());
		Util.regEvents(new Shop());
		Util.regEvents(new JoinQuit());
		Util.regEvents(new MOTD());
		BS.runTaskTimerAsynchronously(this, new CommandDisco(), 0L, 7L);
		Util.regEvents(new CommandDisco());
		Util.regEvents(new CommandRecipe());
		Util.regEvents(new CommandBalance());
	//Config
		if(config.getBoolean("auto pickup")) Util.regEvents(new AutoPickup());
		if(config.getBoolean("block protetion")) Util.regEvents(new Protection());
		if(config.getBoolean("invincible slimes.enabled")) Util.regEvents(new InvincibleSlime());
		if(config.getBoolean("giant loot.enabled")) Util.regEvents(new GiantLoot());
		if(config.getBoolean("command spy.enabled")) Util.regEvents(new CommandSpy());
		if(config.getBoolean("hub effects.enabled"))
		{
			BS.runTaskTimer(this, new HubEffects(), 0L, 20L);
			Util.regEvents(new HubEffects());
		}
		if(config.getBoolean("custom items"))
		{
			Util.regEvents(new LightningRod(), new SonicScrewdriver(), new SandWand());
			Recipes.load();
		}
		if(config.getBoolean("combat log.enabled")) 
		{
			Util.regEvents(new CombatLog());
			CombatLog.start();
		}
		if(config.getBoolean("mob merge.enabled"))
		{
			int period = config.getInt("mob merge.period");
			MobMerge MM = new MobMerge();
			Util.regEvents(MM);
			BS.runTaskTimerAsynchronously(this, MM, period * 20, period * 20);
		}
		if(config.getBoolean("portals.enabled"))
		{
			PortalWand PW = new PortalWand();
			Portal P = new Portal();
			Util.regEvents(PW);
			Util.regEvents(P);
		}
	//Depend Based
		if(PM.isPluginEnabled("Votifier")) Util.regEvents(new VoteLogger());
		if(PM.isPluginEnabled("Vault"))
		{
			BlobcatrazEconomy BE = new BlobcatrazEconomy();
			Vault V = Vault.getPlugin(Vault.class);
			ServicePriority SP = ServicePriority.Highest;
			SM.register(Economy.class, BE, V, SP);
		}
	}
	
	private void enchants()
	{
		boolean enable = config.getBoolean("custom enchants");
		if(enable)
		{
			EnchantListener EL = new EnchantListener();
			EL.start();
			Util.regEvents(EL, new Cure(), new Ender(), new Explosion(), new Fireball(), new Glow(), new InstaKill(), new Levitate(), new LifeSteal(), new Strength(), new Wither(), new XPDrain());
		}
	}
	
	private void commands()
	{
		c("afk", new CommandAFK());
		c("autopickup", new CommandAutoPickup());
		c("balance", new CommandBalance());
		c("baltop", new CommandBalance());
		c("withdraw", new CommandBalance());
		c("ban", new CommandBan());
		c("tempban", new CommandBan());
		c("unban", new CommandBan());
		c("blobcatraz", new CommandBlobcatraz());
		c("chat", new CommandChat());
		c("commandspy", new CommandCommandSpy());
		c("disco", new CommandDisco());
		c("economy", new CommandEconomy());
		c("addlore", new CommandEdit());
        c("clearlore", new CommandEdit());
		c("removelore", new CommandEdit());
		c("rename", new CommandEdit());
		c("repair", new CommandEdit());
		c("resetitem", new CommandEdit());
		c("setlore", new CommandEdit());
		c("enchant", new CommandEnchant());
		c("unbreakable", new CommandEnchant());
		c("findorigin", new CommandFindOrigin());
		c("fly", new CommandFly());
		c("freeze", new CommandFreeze());
		c("unfreeze", new CommandFreeze());
		c("gamemode", new CommandGamemode());
		c("gma", new CommandGamemode());
		c("gmc", new CommandGamemode());
		c("gms", new CommandGamemode());
		c("gmsp", new CommandGamemode());
		c("heal", new CommandHeal());
		c("feed", new CommandHeal());
		c("delhome", new CommandHome());
		c("home", new CommandHome());
		c("sethome", new CommandHome());
		c("clear", new CommandInventory());
		c("item", new CommandItem());
		c("chesttokit", new CommandKit());
		c("createkit", new CommandKit());
		c("deletekit", new CommandKit());
		c("kit", new CommandKit());
		c("kittochest", new CommandKit());
		c("lag", new CommandLag());
		c("tell", new CommandMessage());
		c("reply", new CommandMessage());
		c("getmotd", new CommandMOTD());
		c("setmotd", new CommandMOTD());
		c("portal", new CommandPortal());
		c("random", new CommandRandom());
		c("randomtp", new CommandRandomTP());
		c("recipe", new CommandRecipe());
		c("hub", new CommandSpawn());
		c("spawn", new CommandSpawn());
		c("sethub", new CommandSpawn());
		c("setspawn", new CommandSpawn());
		c("tag", new CommandTag());
		c("center", new CommandTeleport());
		c("tp", new CommandTeleport());
		c("tokenshop", new CommandTokenShop());
		c("vanish", new CommandVanish());
		c("vote", new CommandVote());
		c("delwarp", new CommandWarp());
		c("setwarp", new CommandWarp());
		c("warp", new CommandWarp());
		c("warps", new CommandWarp());
		c("worth", new CommandWorth());
		c("setworth", new CommandWorth());
		c("he", new HubEffects());
	}
	
	private void c(String c, CommandExecutor ce)
	{
		getCommand(c).setExecutor(ce);
	}
}