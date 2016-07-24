package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandVanish implements CommandExecutor
{
	public static List<Player> vanished = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;


		if(label.equalsIgnoreCase("tp"))
		{
			//Teleport codee
		}
		if(label.equalsIgnoreCase("vanish") || label.equalsIgnoreCase("v"))
		{
			if(!vanished.contains(p))
			{
				vanished.add(p);
				p.sendMessage(Util.blobcatraz + "Vanish: enabled");
				for(Player other : Bukkit.getOnlinePlayers())
				{
					other.hidePlayer(p);
				}
			}
			else
			{
				vanished.remove(p);
				p.sendMessage(Util.blobcatraz + "Vanish: disabled");
				for(Player other : Bukkit.getOnlinePlayers())
				{
					other.showPlayer(p);
				}
			}

			return true;
		}
		return false;
	}
}