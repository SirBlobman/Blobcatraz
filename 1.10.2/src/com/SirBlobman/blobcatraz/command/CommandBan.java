package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandBan implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		switch(command)
		{
		case "ban":
			return ban(cs, args);
		case "tempban":
			return tempban(cs, args);
		case "unban":
			return unban(cs, args);
		default:
			return false;
		}
	}

	private boolean ban(CommandSender cs, String[] args)
	{
		String banner = cs.getName();
		if(args.length >= 2)
		{
			String name = args[0];
			@SuppressWarnings("deprecation")
			OfflinePlayer ban = Bukkit.getOfflinePlayer(name);
			String reason = Util.getFinal(args, 1);
			if(!PlayerUtil.hasPermission(cs, "blobcatraz.ban")) return true;
			if(ban == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			ConfigDatabase.ban(banner, ban, reason);
			return true;
		}
		cs.sendMessage(Util.IA);
		return false;
	}
	
	private boolean tempban(CommandSender cs, String[] args)
	{
		String banner = cs.getName();
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.tempban")) return true;
		if(args.length >= 3)
		{
			String name = args[0];
			@SuppressWarnings("deprecation")
			OfflinePlayer ban = Bukkit.getOfflinePlayer(name);
			if(ban == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			try
			{
				String time = args[1];
				String unit = time.substring(time.length() - 1);
				String time2 = time.substring(0, time.length() - 1);
				int length = Integer.parseInt(time2);
				unit = unit.toLowerCase();
				String reason = Util.getFinal(args, 2); 
				
				long millis = 1L;
				long second = millis * 1000L;
				long minute = second * 60L;
				long hour = minute * 60L;
				long day = hour * 24L;
				long week = day * 7L;
				long month = week * 4L;
				long year = month * 12L;
				long decade = year * 10L;
				long century = decade * 10L;
				
				long banTime = 0L;
				if(unit.equals("s")) banTime = length * second;
				if(unit.equals("m")) banTime = length * minute;
				if(unit.equals("h")) banTime = length * hour;
				if(unit.equals("d")) banTime = length * day;
				if(unit.equals("w")) banTime = length * week;
				if(unit.equals("y")) banTime = length * year;
				if(unit.equals("c")) banTime = length * century;
				
				ConfigDatabase.tempban(banner, ban, banTime, reason);
				return true;
			} catch(Exception ex)
			{
				cs.sendMessage(Util.IA);
				return false;
			}
		}
		cs.sendMessage(Util.IA);
		return false;
	}
	
	private boolean unban(CommandSender cs, String[] args)
	{
		String unbanner = cs.getName();
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.unban")) return true;
		if(args.length >= 1)
		{
			String name = args[0];
			@SuppressWarnings("deprecation")
			OfflinePlayer unban = Bukkit.getOfflinePlayer(name);
			if(unban == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			ConfigDatabase.unban(unbanner, unban);
			return true;
		}
		return false;
	}
}