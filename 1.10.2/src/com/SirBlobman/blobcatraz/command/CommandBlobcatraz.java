package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigEmojis;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.enchant.Enchant;
import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CommandBlobcatraz implements CommandExecutor
{
	Server S = Bukkit.getServer();
	PluginManager PM = S.getPluginManager();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equalsIgnoreCase("blobcatraz"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "reload":
					return reload(cs);
				case "restart":
					return restart(cs);
				case "give":
					return give(cs, args);
				case "enchant":
					return enchant(cs, args);
				default:
					return thanks(cs);
				}
			}
			return thanks(cs);
		}
		return false;
	}
	
	private boolean thanks(CommandSender cs)
	{
		String t = Util.blobcatraz + "Sub Commands:";
		String c1 = "§lreload: §rReload all the configs";
		String c2 = "§lrestart: §rRestart the plugin (may cause errors)";
		String c3 = "§lgive: §rCheat in some special items";
		String c4 = "§lenchant: §rEnchant your item with a custom enchantment";
		cs.sendMessage(new String[] {t, c1, c2, c3, c4});
		return true;
	}
	
	private boolean reload(CommandSender cs)
	{
		ConfigBlobcatraz.reload();
		ConfigDatabase.reload();
		ConfigEmojis.reload();
		ConfigKits.reload();
		ConfigLanguage.reload();
		ConfigPortals.reload();
		ConfigShop.reload();
		ConfigSpawn.reload();
		ConfigWarps.reload();
		
		String msg = Util.blobcatraz + "Attempted to reload all configs";
		cs.sendMessage(msg);
		return true;
	}
	
	private boolean restart(CommandSender cs)
	{
		Plugin plugin = Blobcatraz.instance;
		PM.disablePlugin(plugin);
		PM.enablePlugin(plugin);
		return true;
	}
	
	private boolean give(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			
			if(args.length >= 2)
			{
				List<String> valid = Arrays.asList
				(
					"sonic_screwdriver",
					"lightning_rod",
					"op_sword",
					"op_bow",
					"loot_sword",
					"portal_wand"
				);
				Collections.sort(valid);
				String items = String.join(",", valid);
				
				String item = args[1];
				switch(item)
				{
				case "sonic_screwdriver":
					ItemUtil.give(p, BItems.sonicScrewdriver());
					return true;
				case "lightning_rod":
					ItemUtil.give(p, BItems.lightningRod());
					return true;
				case "op_sword":
					ItemUtil.give(p, BItems.op(Material.DIAMOND_SWORD));
					return true;
				case "op_bow":
					ItemUtil.give(p, BItems.op(Material.BOW));
					return true;
				case "loot_sword":
					ItemUtil.give(p, BItems.loot(Material.WOOD_SWORD));
					return true;
				case "portal_wand":
					ItemUtil.give(p, BItems.portalWand());
					return true;
				default:
					String msg = Util.blobcatraz + "§9" + item + " §cdoesn't exist!";
					String msg2 = Util.blobcatraz + "§lValid Items:";
					p.sendMessage(msg);
					p.sendMessage(msg2);
					p.sendMessage(items);
				}
				return true;
			}
			p.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean enchant(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(args.length >= 3)
			{
				String enchant = args[1];
				String lvl = args[2];
				int level = 0;
				try{level = Integer.parseInt(lvl);}
				catch(Exception ex) {p.sendMessage(Util.blobcatraz + "§6" + lvl + " §ris not a Number"); return true;}
				Enchant e;
				try{e = Enchant.valueOf(enchant);}
				catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid enchantment!"); 
					return true;
				}

				ItemUtil.blobcatrazEnchant(ItemUtil.getHeld(p), e, level);
				p.sendMessage(Util.blobcatraz + "Attempted to enchant the item you are holding");
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}