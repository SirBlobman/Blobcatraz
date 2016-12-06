package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandItem implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("item"))
			{
				if(args.length > 0)
				{
					String permission = "blobcatraz.item";
					if(!PlayerUtil.hasPermission(p, permission)) return true;
					
					Material mat;
					int amount = 1;
					short meta = 0;

					String[] item = args[0].split(":");
					if(item.length > 1)
					{
						try{meta = Short.parseShort(item[1]);}
						catch(Exception ex) 
						{
							String msg = Util.prefix + Util.option("command.item.invalid meta", item[1]);
							p.sendMessage(msg);
							return true;
						}
					}
					mat = Material.matchMaterial(item[0]);
					if(mat == null) 
					{
						String msg = Util.prefix + Util.option("command.item.invalid item", item[0]);
						p.sendMessage(msg);
						return true;
					}

					if(args.length > 1)
					{
						try{amount = Integer.parseInt(args[1]);}
						catch(Exception ex)
						{
							String msg = Util.prefix + Util.option("command.item.invalid amount", args[1]);
							p.sendMessage(msg);
							return true;
						}
					}
					
					ItemStack is = new ItemStack(mat, amount, meta);
					ItemUtil.give(p, is);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("item"))
		{
			if(args.length == 1)
			{
				String arg = args[0];
				List<String> items = ItemUtil.items();
				return Util.getMatching(items, arg);
			}
		}
		return null;
	}
}