package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandVanish implements CommandExecutor 
{
	public static List<Player> vanish = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("vanish"))
			{
				String permission = "blobcatraz.vanish";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				PotionEffectType invis = PotionEffectType.INVISIBILITY;
				
				if(vanish.contains(p))
				{
					vanish.remove(p);
					p.sendMessage(Util.blobcatraz + "Vanish: §4OFF");
					p.removePotionEffect(invis);
					for(Player p2 : Bukkit.getOnlinePlayers()) p2.showPlayer(p);
					return true;
				}
				
				vanish.add(p);
				p.sendMessage(Util.blobcatraz + "Vanish: §2ON");
				PotionEffect pe = new PotionEffect(invis, Integer.MAX_VALUE, 5, true);
				p.addPotionEffect(pe, true);
				for(Player p2 : Bukkit.getOnlinePlayers()) p2.hidePlayer(p);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}