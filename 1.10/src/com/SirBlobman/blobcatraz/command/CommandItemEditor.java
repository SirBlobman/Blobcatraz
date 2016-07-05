package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandItemEditor implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("rename"))
		{
			if(args.length > 0)
			{
				String newName = Util.getFinalArg(args, 0);
				
				Util.rename(p, newName);
				return true;
			}
			else
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
		}
		if(label.equalsIgnoreCase("setlore"))
		{
			if(args.length > 0)
			{
				String lore = Util.getFinalArg(args, 0);
				
				Util.setLore(p, lore);
				return true;
			}
			else
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
		}
		if(label.equalsIgnoreCase("addlore"))
		{
			if(args.length > 0)
			{
				String lore = Util.getFinalArg(args, 0);
				
				Util.addLore(p, lore);
				return true;
			}
			else
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
		}
		if(label.equalsIgnoreCase("removelore") || label.equalsIgnoreCase("deletelore"))
		{
			if(args.length == 1)
			{
				int line = Integer.parseInt(args[0]) - 1;
				Util.removeLore(p, line);
				return true;
			}
			
			Util.removeLore(p);
		}
		if(label.equalsIgnoreCase("repair") || label.equalsIgnoreCase("fix"))
		{
			Util.repairItem(p);
			return true;
		}
		if(label.equalsIgnoreCase("resetitem"))
		{
			Util.resetItem(p);
			return true;
		}
        return false;
    }
}