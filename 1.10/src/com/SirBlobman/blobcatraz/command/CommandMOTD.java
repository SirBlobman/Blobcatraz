package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;

public class CommandMOTD implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("setmotd"))
		{
			if(args.length > 0)
			{
				ConfigLanguage.setMessage("motd", Util.getFinalArg(args, 0));

				String motd = ConfigLanguage.getMessage("motd");
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