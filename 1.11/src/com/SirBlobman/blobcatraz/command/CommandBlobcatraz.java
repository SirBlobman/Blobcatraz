package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.enchant.Enchant;
import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandBlobcatraz implements CommandExecutor, TabCompleter
{
	private static final List<String> items = Arrays.asList("sonic_screwdriver", "lightning_rod", "op_sword", "op_bow", "op_pickaxe", "sand_wand", "sand_wand2");
	private static final List<String> enchants = null;
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("blobcatraz"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "reload": return reload(cs);
				case "restart": return reload(cs);
				case "give": return give(cs, args);
				case "enchant": return enchant(cs, args);
				default: return thanks(cs);
				}
			}
			return thanks(cs);
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		if(args.length < 2) return null;
		String arg = args[0].toLowerCase();
		String arg2 = args[1];
		if(arg.equals("enchant"))
		{
			List<String> m = Util.getMatching(enchants, arg2);
			return m;
		}
		if(arg.equals("give"))
		{
			List<String> m = Util.getMatching(items, arg2);
			return m;
		}
		return null;
	}
	
	private boolean thanks(CommandSender cs)
	{
		String[] msg = new String[]
		{
			Util.format(Util.prefix + "Sub Commands:"),
			Util.format("&lreload: &rReload all the configs"),
			Util.format("&lrestart: &rRestart the plugin (may cause errors)"),
			Util.format("&lgive: &rCheat in a special item"),
			Util.format("&lenchant: &rAdd custom enchantments to the item you are holding")
		};
		cs.sendMessage(msg);
		return true;
	}
	
	private boolean reload(CommandSender cs)
	{
		String msg = Util.format("This feature was removed because it was useless");
		cs.sendMessage(msg);
		return true;
	}
	
	private boolean give(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(args.length >= 2)
			{
				Collections.sort(items);
				String sitems = String.join(",", items);
				
				String item = args[1].toLowerCase();
				switch(item)
				{
				case "sonic_screwdriver": ItemUtil.give(p, BItems.sonicScrewdriver());
				case "lightning_rod": ItemUtil.give(p, BItems.lightningRod());
				case "op_sword": ItemUtil.give(p, BItems.op(Material.DIAMOND_SWORD));
				case "op_pickaxe": ItemUtil.give(p, BItems.op(Material.DIAMOND_PICKAXE));
				case "op_bow": ItemUtil.give(p, BItems.op(Material.BOW));
				case "loot_sword": ItemUtil.give(p, BItems.loot(Material.WOOD_SWORD));
				case "portal_wand": ItemUtil.give(p, BItems.portalWand());
				case "sand_wand": ItemUtil.give(p, BItems.sandWand());
				case "sand_wand2": ItemUtil.give(p, BItems.uSandWand());
				default:
					String[] msg = new String[]
					{
						Util.prefix + Util.format("&9%s &cdoesn't exist", item),
						Util.prefix + Util.format("&lValid Items:")
					};
					p.sendMessage(msg);
					p.sendMessage(sitems);
				}
				return true;
			}
			p.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean enchant(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(args.length >= 3)
			{
				String enchant = args[1].toUpperCase();
				String level = args[2];
				int lvl = 0;
				try{lvl = Integer.parseInt(level);}
				catch(Exception ex) {p.sendMessage(Util.prefix + Util.option("error.number.parse",level)); return true;}
				Enchant e;
				try{e = Enchant.valueOf(enchant);}
				catch(Exception ex) {p.sendMessage(Util.prefix + Util.option("error.enchant.invalid", enchant)); return true;}
				
				ItemStack held = ItemUtil.held(p);
				ItemUtil.bEnchant(held, e, lvl);
				p.sendMessage(Util.prefix + Util.option("command.blobcatraz.enchant.success", ItemUtil.name(held), e, level));
				return true;
			}
			p.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}