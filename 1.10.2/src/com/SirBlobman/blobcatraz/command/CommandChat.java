package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommandChat implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String name = cs.getName();
		String command = c.getName().toLowerCase();
		YamlConfiguration config = ConfigBlobcatraz.load();
		
		if(command.equals("chat"))
		{
			if(args.length >= 1)
			{
				String sub = args[0].toLowerCase();
				if(sub.equals("status"))
				{
					boolean status = config.getBoolean("chat.disabled");
					String stat = Util.blobcatraz + "The chat is: ";
					String on = stat + "§2ON";
					String off = stat + "§4OFF";
					
					if(status) cs.sendMessage(on);
					else cs.sendMessage(off);
					return true;
				}
				if(sub.equals("on"))
				{
					config.set("chat.disabled", false);
					ConfigBlobcatraz.save();
					Util.broadcast("§5" + name + " §ehas §9un-muted§e the chat!");
					return true;
				}
				if(sub.equals("off"))
				{
					config.set("chat.disabled", true);
					ConfigBlobcatraz.save();
					Util.broadcast("§5" + name + " §ehas §9muted§e the chat!");
					return true;
				}
				if(sub.equals("clear"))
				{
					int line = 1;
					while(line <= 500)
					{
						Bukkit.broadcastMessage("§1");
						line++;
					}
					Util.broadcast("§5" + name + " §ehas §9cleared§e the chat!");
					return true;
				}
				cs.sendMessage(Util.IA);
				return false;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
}