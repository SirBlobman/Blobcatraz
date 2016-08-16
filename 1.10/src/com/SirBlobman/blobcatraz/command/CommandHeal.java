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
				
				String permission = "blobcatraz.heal";
				if(p.hasPermission(permission)) Util.heal(p);
				else Util.noPermission(p, permission);
				
				return true;
			}
			if(args.length == 1)
			{
				Player toHeal = Bukkit.getPlayer(args[0]);
				if(toHeal == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a Player"); return true;}
				
				String permission = "blobcatraz.heal.others";
				if(cs.hasPermission(permission)) Util.heal(toHeal);
				else Util.noPermission(cs, permission);
				
				return true;
			}
		}
		if(label.equalsIgnoreCase("feed") || label.equalsIgnoreCase("eat"))
		{
			if(args.length == 0)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				String permission = "blobcatraz.feed";
				if(p.hasPermission(permission)) Util.feed(p);
				else Util.noPermission(p, permission);
				
				return true;
			}
			if(args.length == 1)
			{
				Player toFeed = Bukkit.getPlayer(args[0]);
				if(toFeed == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a Player"); return true;}
				
				String permission = "blobcatraz.feed.others";
				if(cs.hasPermission(permission)) Util.feed(toFeed);
				else Util.noPermission(cs, permission);
				
				return true;
			}
		}
        return false;
    }
}