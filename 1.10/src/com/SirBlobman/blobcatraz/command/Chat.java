package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.BlobcatrazConfig;
import com.SirBlobman.blobcatraz.listeners.ChatMute;

public class Chat implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		String nsender = sender.getName();
		BlobcatrazConfig.loadConfig();
		
		if(label.equalsIgnoreCase("chat"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("status"))
				{
					boolean status = BlobcatrazConfig.config.getBoolean("chat.disabled");
					if(status == true)
					{
						sender.sendMessage(Util.blobcatraz + "§eThe chat is: §4Off");
					}
					if(status == false)
					{
						sender.sendMessage(Util.blobcatraz + "§eThe chat is: §2On");
					}
				}
				if(args[0].equalsIgnoreCase("on"))
				{
					BlobcatrazConfig.config.set("chat.disabled", false);
					BlobcatrazConfig.config.set("chat.disabled", false);
					BlobcatrazConfig.config.set("chat.disabled", false);
					BlobcatrazConfig.config.set("chat.disabled", false);
					BlobcatrazConfig.config.set("chat.disabled", false);
					BlobcatrazConfig.config.set("chat.disabled", false);
					ChatMute.isGlobalMute = false;
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.reloadConfig();
					Util.broadcast("§5" + nsender + " §ehas §9un-muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("off"))
				{
					BlobcatrazConfig.config.set("chat.disabled", true);
					BlobcatrazConfig.config.set("chat.disabled", true);
					BlobcatrazConfig.config.set("chat.disabled", true);
					BlobcatrazConfig.config.set("chat.disabled", true);
					BlobcatrazConfig.config.set("chat.disabled", true);
					BlobcatrazConfig.config.set("chat.disabled", true);
					ChatMute.isGlobalMute = true;
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.saveConfig();
					Blobcatraz.instance.reloadConfig();
					Util.broadcast("§5" + nsender + " §ehas §9muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("clear"))
				{
					int line = 1;
					while(line <= 500)
					{
						Bukkit.broadcastMessage("§l");
						line++;
					}
					Util.broadcast("§5" + nsender + " §ehas §9cleared§e the chat!"); 
					return true;
				}
			}
		}
		
		return false;
	}
}
