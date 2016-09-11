package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAutoPickup implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("autopickup"))
			{
				String permission = "blobcatraz.autopickup.toggle";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				String enabled = Util.blobcatraz + Util.message("command.autopickup.enabled");
				String disabled = Util.blobcatraz + Util.message("command.autopickup.disabled");
				if(ConfigDatabase.toggleAutoPickup(p)) p.sendMessage(enabled);
				else p.sendMessage(disabled);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}