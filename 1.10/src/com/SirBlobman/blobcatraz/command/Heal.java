package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.SirBlobman.blobcatraz.Util;

public class Heal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command s, String label, String[] args)
	{
		
		
		if(label.equalsIgnoreCase("heal"))
		{
			if(args.length == 0)
			{
				if(!(cs instanceof Player))
				{
					cs.sendMessage(Util.notAPlayer);
					return true;
				}
				Player p = (Player)cs;
				
				if(p.hasPermission("blobcatraz.heal"))
				{
					heal(p);
				}
				else
				{
					p.sendMessage(Util.noPerms + "blobcatraz.heal");
				}
				
				return true;
			}
			if(args.length >= 1)
			{
				Player toHeal = Bukkit.getPlayer(args[0]);
				
				if(cs.hasPermission("blobcatraz.heal.others"))
				{
					try
					{
						heal(toHeal);
					}
					catch(Exception ex)
					{
						cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " is not a valid Player");
					}
					
				}
				else
				{
					cs.sendMessage(Util.noPerms + "blobcatraz.heal.others");
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	public static void heal(Player p)
	{
		if(p == null)
		{
			return;
		}
		
		p.setHealth(20.0);
		for(PotionEffect potion : p.getActivePotionEffects())
		{
			p.removePotionEffect(potion.getType());
		}
		p.setFireTicks(0);
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		p.sendMessage(Util.blobcatraz + "You have been healed");
	}
}