package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandMOTD implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("setmotd"))
		{
			if(args.length > 0)
			{
				String permission = "blobcatraz.setmotd";
				if(!PlayerUtil.hasPermission(cs, permission)) return true;
				
				String motd = Util.getFinal(args, 0);
				ConfigLanguage.set("motd", motd, true);
				
				String cmotd = Util.color(motd);
				String msg = Util.prefix + Util.option("command.setmotd.success", cmotd);
				cs.sendMessage(msg);
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
}