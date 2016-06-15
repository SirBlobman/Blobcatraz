package com.SirBlobman.blobcatraz.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.SirBlobman.blobcatraz.Util;

public class I implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command s, String label, String[] args)
	{
		if(!(cs instanceof Player)) 
		{
			cs.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("i"))
		{
			if(!(args.length == 3))
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
			else
			{
				ItemStack is = new ItemStack(Material.getMaterial(args[0]));
				int amount = new Integer(args[1]);
				short damage = new Short(args[2]);
				
				is.setAmount(amount);
				is.setDurability(damage);
				
				p.sendMessage(Util.blobcatraz + "You have been given " + amount + "of " + is.getType().toString() + ":" + damage);
				Util.giveItem(p, is);
				
				return true;
			}
		}
		
		return false;
	}
}