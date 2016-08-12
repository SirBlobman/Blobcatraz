package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandHome implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("home"))
		{
			if(!p.hasPermission("blobcatraz.home")) {p.sendMessage(Util.noPermission + "blobcatraz.home"); return true;}
			if(args.length == 0)
			{
				List<String> homeList = ConfigDatabase.getHomes(p);
				StringBuffer homes = new StringBuffer();
				for(int i = 0; i < homeList.size(); i++)
				{
					if(i!=0) homes.append(Util.color("&r, "));
					homes.append("§6" + homeList.get(i));
				}
				cs.sendMessage(Util.blobcatraz + "Homes:");
				cs.sendMessage(homes.toString());
				return true;
			}
			String home = Util.getFinalArg(args, 0);
			ConfigDatabase.teleportHome(home, p);
			p.sendMessage(Util.blobcatraz + "You are now home!");
			return true;
		}
		if(label.equalsIgnoreCase("sethome"))
		{
			if(!p.hasPermission("blobcatraz.sethome")) {p.sendMessage(Util.noPermission + "blobcatraz.sethome"); return true;}
			if(ConfigDatabase.getHomes(p).size() >= 1 && !p.hasPermission("blobcatraz.sethome.multiple")) {p.sendMessage(Util.blobcatraz + "You already have a home!"); return true;}
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
			String home = Util.getFinalArg(args, 0);
			ConfigDatabase.setHome(p, p.getLocation(), home);
			p.sendMessage(Util.blobcatraz + "Your home has been set!");
			return true;
		}
		return false;
	}
}