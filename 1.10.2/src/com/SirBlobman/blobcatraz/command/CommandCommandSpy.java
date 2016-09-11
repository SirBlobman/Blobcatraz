package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandCommandSpy implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("commandspy"))
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				FileConfiguration config = ConfigBlobcatraz.load();
				if(!PlayerUtil.hasPermission(p, "blobcatraz.commandspy")) return true;
				
				boolean enabled = config.getBoolean("command spy.enabled");
				if(enabled)
				{
					boolean spy = ConfigDatabase.toggleCanSpy(p);
					if(spy) p.sendMessage("CommandSpy is §2ON");
					else p.sendMessage("CommandSpy is §4OFF");
					return true;
				}
				p.sendMessage(Util.blobcatraz + "CommandSpy is not enabled in the config!");
				return true;
			}
		}
		return false;
	}
}