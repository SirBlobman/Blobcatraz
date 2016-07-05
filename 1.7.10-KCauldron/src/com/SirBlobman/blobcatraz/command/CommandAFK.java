package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandAFK implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		String name = p.getName();
		
		if(label.equalsIgnoreCase("afk"))
		{
			if(args.length < 1)
			{
				Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK");
				ConfigDatabase.setAFK(p);
			}
			else
			{
				String reason = Util.color(Util.getFinalArg(args, 0));
				
				Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK:");
				Bukkit.broadcastMessage("      - " + reason);
				
				ConfigDatabase.setAFK(p);
			}
			return true;
		}
		return false;
	}
}