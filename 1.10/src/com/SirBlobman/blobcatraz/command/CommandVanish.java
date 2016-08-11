package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.Util;

public class CommandVanish implements CommandExecutor
{
	public static List<Player> vanished = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("vanish") || label.equalsIgnoreCase("v"))
		{
			if(!p.hasPermission("blobcatraz.vanish")) {p.sendMessage(Util.noPermission + "blobcatraz.vanish"); return true;}
			if(!vanished.contains(p))
			{
				vanished.add(p);
				p.sendMessage(Util.blobcatraz + "Vanish: enabled");
				PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 5, true);
				p.addPotionEffect(pe, true);
				for(Player other : Bukkit.getOnlinePlayers())
				{
					other.hidePlayer(p);
				}
			}
			else
			{
				vanished.remove(p);
				p.sendMessage(Util.blobcatraz + "Vanish: disabled");
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
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