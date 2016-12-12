package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandInventory implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("openinv"))
			{
				String permission = "blobcatraz.openinv";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length > 0)
				{
					String name = args[0];
					Player target = Bukkit.getPlayer(name);
					if(target == null)
					{
						p.sendMessage(Util.prefix + Util.option("error.target.not online", name));
						return true;
					}
					
					PlayerInventory pi = target.getInventory();
					p.openInventory(pi);
					return true;
				}
				cs.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}