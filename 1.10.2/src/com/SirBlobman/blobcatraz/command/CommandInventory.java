package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInventory implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		String name = cs.getName();
		
		if(command.equals("clear"))
		{
			String permission = "blobcatraz.clearinventory";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			if(args.length == 0)
			{
				if(cs instanceof Player)
				{
					Player p = (Player) cs;
					return self(p, "");
				}
				cs.sendMessage(Util.csNotPlayer);
				return true;
			}
			
			String pname = args[0];
			Player p = Bukkit.getPlayer(pname);
			if(p == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + pname + " §ris not a Player");
				return true;
			}
			
			if(args.length == 1)
			{
				if(name.equals(pname)) return self(cs, "");
				return other(cs, p, "");
			}
			
			String type = args[1].toLowerCase();
			return other(cs, p, type);
		}
		return false;
	}
	
	private boolean self(CommandSender cs, String type)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(type.equals("")) PlayerUtil.clear(p);
			else if(type.equals("ec") || type.equals("enderchest") || type.equals("echest")) PlayerUtil.clearEnderChest(p);
			else if(type.equals("armor") || type.equals("a")) PlayerUtil.clearArmor(p);
			else
			{
				cs.sendMessage(Util.IA);
				return false;
			}
			return true;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean other(CommandSender cs, Player p, String type)
	{
		String permission = "blobcatraz.clearinventory.other";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(type.equals("")) PlayerUtil.clear(p);
		else if(type.equals("ec") || type.equals("enderchest") || type.equals("echest")) PlayerUtil.clearEnderChest(p);
		else if(type.equals("armor") || type.equals("a")) PlayerUtil.clearArmor(p);
		else
		{
			cs.sendMessage(Util.IA);
			return false;
		}
		return true;
	}
}