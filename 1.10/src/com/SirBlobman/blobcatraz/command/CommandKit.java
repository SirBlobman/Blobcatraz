package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigKits;

public class CommandKit implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		if(label.equalsIgnoreCase("kit"))
		{
			if(!p.hasPermission("blobcatraz.kit")) {Util.noPermission(p, "blobcatraz.kit"); return true;}
			if(args.length == 0)
			{
				if(p.hasPermission("blobcatraz.kits"))
				{
					StringBuffer kitList = new StringBuffer();
					for(int i = 0; i < ConfigKits.getKits().size(); i++)
					{
						String kitName = ConfigKits.getKits().get(i);
						if(cs.hasPermission("blobcatraz.kits."  + kitName))
						{
							if(i != 0) kitList.append("§r, ");
							kitList.append("§2" + kitName);
						}
					}
					p.sendMessage(Util.blobcatraz + "List of Kits:");
					p.sendMessage(kitList.toString());
					return true;
				}
				else
				{
					Util.noPermission(p, "blobcatraz.kits");
					return true;
				}
			}
			String kitName = Util.getFinalArg(args, 0);
			if(ConfigKits.doesKitExist(kitName))
			{
				if(p.hasPermission("blobcatraz.kits." + kitName))
				{
					ConfigKits.giveKitToPlayer(p, kitName);
					p.sendMessage(Util.blobcatraz + "You have been given kit §c" + kitName);
					return true;
				}
				else
				{
					Util.noPermission(p, "blobcatraz.kits." + kitName);
					return true;
				}
			}
			else
			{
				p.sendMessage(Util.blobcatraz + "That kit doesn't exist");
				return true;
			}
		}
		if(label.equalsIgnoreCase("createkit"))
		{
			if(!p.hasPermission("blobcatraz.createkit")) {Util.noPermission(p, "blobcatraz.createkit"); return true;}
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
			String kitName = Util.getFinalArg(args, 0);
			PlayerInventory pi = p.getInventory();
			ConfigKits.createKit(pi, kitName);
			p.sendMessage(Util.blobcatraz + "You have created a kit called §c" + kitName);
			return true;
		}
		if(label.equalsIgnoreCase("deletekit"))
		{
			if(!p.hasPermission("blobcatraz.deletekit")) {Util.noPermission(p, "blobcatraz.createkit"); return true;}
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
			String kitName = Util.getFinalArg(args, 0);
			ConfigKits.deleteKit(kitName);
			p.sendMessage(Util.blobcatraz + "Kit '§c" + kitName + "§r' has been deleted!");
			return true;
		}
		return false;
	}
}