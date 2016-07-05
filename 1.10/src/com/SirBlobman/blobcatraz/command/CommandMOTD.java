package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class CommandMOTD implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("setmotd"))
		{
			if(args.length > 0)
			{
				ConfigBlobcatraz.loadConfig();
				ConfigBlobcatraz.config.set("motd", Util.getFinalArg(args, 0));
				ConfigBlobcatraz.saveConfig();

				String motd = Util.color(ConfigBlobcatraz.config.getString("motd"));
				cs.sendMessage(Util.blobcatraz + "Changed the MOTD to " + motd);
				return true;
			}
			else
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}
		}
        return false;
    }
}