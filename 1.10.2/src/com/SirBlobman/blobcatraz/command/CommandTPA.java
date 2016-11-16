package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.Util;

public class CommandTPA implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("tpa")) return tpa(cs, args);
		//if(command.equals("tpahere")) return tpahere(cs, args);
		//if(command.equals("tpaccept")) return allow(cs, args);
		//if(command.equals("tpdeny")) return deny(cs, args);
		return false;
	}
	
	private boolean tpa(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			return false;
		}
		
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}