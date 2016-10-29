package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandOpenInv implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("openinv"))
			{
				String permission = "blobcatraz.openinv";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length == 1)
				{
					String name = args[0];
					Player open = Bukkit.getPlayer(name);
					if(open != null)
					{
						PlayerInventory pi = open.getInventory();
						p.openInventory(pi);
						return true;
					}
					p.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}