package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandHeal implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("heal"))
		{
			if(args.length == 0)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				if(p.hasPermission("blobcatraz.heal")) Util.heal(p);
				else p.sendMessage(Util.noPermission + "blobcatraz.heal");
				
				return true;
			}
			if(args.length == 1)
			{
				Player toHeal = Bukkit.getPlayer(args[0]);
				if(toHeal == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a Player"); return true;}
				
				if(cs.hasPermission("blobcatraz.heal.others")) Util.heal(toHeal);
				else cs.sendMessage(Util.noPermission + "blobcatraz.heal.others");
				
				return true;
			}
		}
        return false;
    }
}