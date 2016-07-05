package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandFly implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("fly") || label.equalsIgnoreCase("soar"))
		{
			String enabled = "§2ENABLED";
			String disabled = "§4DISABLED";
			
			if(!p.hasPermission("blobcatraz.fly")) {p.sendMessage(Util.noPermission + "blobcatraz.fly"); return true;}
			
			if(args.length == 1)
			{
				if(!p.hasPermission("blobcatraz.fly.toggle")) {p.sendMessage(Util.noPermission + "blobcatraz.fly.toggle"); return true;}
				
				if(args[0].equalsIgnoreCase("on"))
				{
					p.setAllowFlight(true);
					p.sendMessage(Util.blobcatraz + "Flight has been " + enabled);
					return true;
				}
				if(args[0].equalsIgnoreCase("off"))
				{
					p.setAllowFlight(false);
					p.sendMessage(Util.blobcatraz + "Flight has been " + disabled);
					return true;
				}
				return false;
			}
			
			if(p.getAllowFlight() == true) p.sendMessage(Util.blobcatraz + "Your flight is " + enabled);
			if(p.getAllowFlight() == false) p.sendMessage(Util.blobcatraz + "Your flight is " + disabled);
			return true;
		}
        return false;
    }
}