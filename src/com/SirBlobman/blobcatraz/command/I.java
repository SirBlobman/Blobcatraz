package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Shop;

public class I implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command s, String label, String[] args)
	{
		if(!(cs instanceof Player)) 
		{
			cs.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("i") || label.equalsIgnoreCase("item") || label.equalsIgnoreCase("selfgive"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("list"))
				{
					ArrayList<String> ilist = Shop.getItemList();
					Collections.sort(ilist);
					
					p.sendMessage(Util.blobcatraz + "Valid material list:");
					for(String i : ilist)
					{
						p.sendMessage(i);
					}
					p.sendMessage(Util.blobcatraz + "You might want to check your §6latest.log§r if your chat cannot see all the items");
					return true;
				}
				else
				{
					ItemStack is = new ItemStack(Material.getMaterial(args[0]));
					p.sendMessage(Util.blobcatraz + "You have been given 1 of " + is.getType().toString() + ":0");
					Util.giveItem(p, is);
					return true;
				}
			}
			if(args.length == 2)
			{
				ItemStack is = new ItemStack(Material.getMaterial(args[0]));
				int amount = Integer.parseInt(args[1]);
				
				is.setAmount(amount);
				
				p.sendMessage(Util.blobcatraz + "You have been given " + amount + "of " + is.getType().toString() + ":0");
				Util.giveItem(p, is);
				
				return true;
			}
			if(args.length == 3)
			{
				ItemStack is = new ItemStack(Material.getMaterial(args[0]));
				int amount = new Integer(args[1]);
				short damage = new Short(args[2]);
				
				is.setAmount(amount);
				is.setDurability(damage);
				
				p.sendMessage(Util.blobcatraz + "You have been given " + amount + "of " + is.getType().toString() + ":" + damage);
				Util.giveItem(p, is);
				
				return true;
			}
			else
			{
				p.sendMessage(Util.invalidArguments);
			}
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("i") || label.equalsIgnoreCase("item"))
		{
			List<String> ilist = Shop.getItemList();
			ilist.add("list");
			Collections.sort(ilist);
			return ilist;
		}
		
		return null;
	}
}