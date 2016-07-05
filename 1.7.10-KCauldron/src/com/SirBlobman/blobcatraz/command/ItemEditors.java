package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class ItemEditors implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("rename"))
		{
			if(args.length > 0)
			{
				Util.rename(p, Util.getFinalArg(args, 0));
				p.sendMessage(Util.blobcatraz + "Attempted to rename your item to " + Util.color(Util.getFinalArg(args,  0)) + "§r!");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("setlore"))
		{
			Util.setLore(p, Util.getFinalArg(args, 0));

			cs.sendMessage(Util.blobcatraz + "Set lore to " + p.getItemInHand().getItemMeta().getLore());
			p.updateInventory();
			return true;
		}

		if(label.equalsIgnoreCase("addlore"))
		{
			Util.addLore(p, Util.getFinalArg(args, 0));

			p.sendMessage(Util.blobcatraz + "Set lore to " + p.getItemInHand().getItemMeta().getLore());
			p.updateInventory();
			return true;
		}

		if(label.equalsIgnoreCase("repair") || label.equalsIgnoreCase("fix"))
		{
			Util.repairItem(p);
			return true;
		}

		if(label.equalsIgnoreCase("resetitem"))
		{
			Util.resetItem(p);
			p.sendMessage(Util.blobcatraz + "Your §e" + p.getItemInHand().getType().toString() + "§r has been reset!");
			return true;
		}
		
		return false;
	}
}