package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CommandHeal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("heal"))
		{
			int i = args.length;
			if(i == 0) return selfH(cs);
			if(i >= 1)
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return heal(cs, p);
			}
		}
		if(command.equals("feed"))
		{
			int i = args.length;
			if(i == 0) return selfF(cs);
			if(i >= 1)
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return feed(cs, p);
			}
		}
		return false;
	}

	private boolean selfH(CommandSender cs)
	{
		String permission = "blobcatraz.heal";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			PlayerUtil.heal(le);
			String msg = Util.message("command.heal.sucess");
			le.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.csNotLiving);
		return true;
	}
	
	private boolean selfF(CommandSender cs)
	{
		String permission = "blobcatraz.feed";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			PlayerUtil.feed(p);
			String msg = Util.message("command.feed.success");
			p.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}

	private boolean heal(CommandSender cs, Player heal)
	{
		String cname = cs.getName();
		String pname = heal.getName();
		
		String permission = "blobcatraz.heal.others";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		PlayerUtil.heal(heal);
		String msg = Util.blobcatraz + Util.message("command.heal.success");
		String msg2 = Util.blobcatraz + Util.message("command.heal.success.others", pname);
		heal.sendMessage(msg);
		if(!cname.equals(pname)) cs.sendMessage(msg2);
		return true;
	}
	
	private boolean feed(CommandSender cs, Player feed)
	{
		String cname = cs.getName();
		String pname = feed.getName();
		
		PlayerUtil.feed(feed);
		String msg = Util.blobcatraz + Util.message("command.feed.success");
		String msg2 = Util.blobcatraz + Util.message("command.feed.success.others", pname);
		feed.sendMessage(msg);
		if(!cname.equals(pname)) cs.sendMessage(msg2);
		return true;
	}
}