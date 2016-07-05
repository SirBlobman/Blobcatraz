package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.item.Items;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;

public class CommandBlobcatraz implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		Server s = Bukkit.getServer();
		PluginManager pm = s.getPluginManager();
		
		if(label.equalsIgnoreCase("blobcatraz"))
		{
			if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("restart"))
				{
					pm.disablePlugin(Blobcatraz.instance);
					pm.enablePlugin(Blobcatraz.instance);
					cs.sendMessage(Util.blobcatraz + "Attempted to restart Blobcatraz");
					return true;
				}
				if(args[0].equalsIgnoreCase("reload"))
				{
					ConfigBlobcatraz.reloadConfig();
					ConfigPortals.loadPortals();
					ConfigDatabase.loadDatabase();
					ConfigShop.loadPrices();

					cs.sendMessage(Util.blobcatraz + "Configs have been reloaded");
					return true;
				}
				if(args[0].equalsIgnoreCase("give"))
				{
					Player p = (Player) cs;
					if(p == null)
					{
						cs.sendMessage(Util.commandExecutorNotAPlayer);
						return true;
					}

					if(args.length == 2)
					{
						List<String> validItems = Arrays.asList("sonic_screwdriver", "lightning_rod", "op_sword", "op_bow", "loot_sword", "portal_wand");
						String itemList = String.join("\n", validItems);

						if(args[1].equalsIgnoreCase("sonic_screwdriver")) {Util.giveItem(p, SonicScrewdriver.sonicScrewdriver()); return true;}
						if(args[1].equalsIgnoreCase("lightning_rod")) {Util.giveItem(p, LightningRod.lightningRod()); return true;}
						if(args[1].equalsIgnoreCase("op_sword")) {Util.giveItem(p, Items.opSword()); return true;}
						if(args[1].equalsIgnoreCase("op_bow")) {Util.giveItem(p, Items.opBow()); return true;}
						if(args[1].equalsIgnoreCase("portal_wand")) {Util.giveItem(p, Items.portalWand()); return true;}
						else
						{
							p.sendMessage(Util.blobcatraz + "That item doesn't exist!\n§cValid Items:");
							p.sendMessage(itemList + "\n");
							return true;
						}
					}
					else
					{
						p.sendMessage(Util.notEnoughArguments);
					}
				}
				if(args[0].equals("enchant"))
				{
					Player p = (Player) cs;
					if(p == null)
					{
						cs.sendMessage(Util.commandExecutorNotAPlayer);
						return true;
					}

					String enchant = args[1];
					int level = Integer.parseInt(args[2]);

					Util.enchantItem(p, enchant, level);
					p.sendMessage(Util.blobcatraz + "Attempted to enchant your item with §7" + enchant + " " + level);
					return true;
				}
			}
			else
			{
				cs.sendMessage(Util.blobcatraz + "Thanks for testing /blobcatraz");
				cs.sendMessage(Util.blobcatraz + "Proper usage:\n");
				cs.sendMessage(c.getDescription() + "\n");
				cs.sendMessage(c.getUsage());
				return true;
			}
		}
		return false;
	}
}