package com.SirBlobman.blobcatraz.command;

import java.util.Collections;
import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem implements CommandExecutor,TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			
			String command = c.getName().toLowerCase();
			if(command.equals("item"))
			{
				String permission = "blobcatraz.item";
				if(!PlayerUtil.hasPermission(cs, permission)) return true;
				if(args.length == 0)
				{
					cs.sendMessage(Util.NEA);
					return false;
				}
				
				String sub = args[0].toLowerCase();
				if(sub.equals("list")) return list(cs);
				
				String item = args[0].toUpperCase();
				Material mat = Material.matchMaterial(item);
				if(mat == null)
				{
					String msg = Util.message("command.item.invalidMaterial", item);
					p.sendMessage(msg);
					return true;
				}
				ItemStack is = new ItemStack(mat);
				if(args.length == 1) 
				{
					ItemUtil.give(p, is);
					return true;
				}
				if(args.length >= 2)
				{
					int amount = 0;
					try{amount = Integer.parseInt(args[1]);}
					catch(Exception ex)
					{
						String msg = Util.message("command.item.invalidAmount", args[1]);
						p.sendMessage(msg);
						return true;
					}
					is.setAmount(amount);
				}
				if(args.length >= 3)
				{
					short damage = 0;
					try{damage = Short.parseShort(args[2]);}
					catch(Exception ex)
					{
						String msg = Util.message("command.item.invalidMeta", args[1]);
						p.sendMessage(msg);
						return true;
					}
					is.setDurability(damage);
				}
				ItemUtil.give(p, is);
				return true;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean list(CommandSender cs)
	{
		List<String> items = ConfigShop.getMaterialNames();
		Collections.sort(items);
		String list = String.join("§r, §2", items);
		cs.sendMessage(Util.blobcatraz + "Valid item list:");
		cs.sendMessage("§2" + list.toLowerCase());
		cs.sendMessage(Util.blobcatraz + "You might want to check your §6latest.log§r if you cannot see the list");
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("item"))
		{
			List<String> list = ConfigShop.getMaterialNames();
			list.add("list");
			Collections.sort(list);

			if(args.length == 1)
			{				
				List<String> actual = Util.getMatchingStrings(list, args[0]);
				return actual;
			}
		}
		return null;
	}
}