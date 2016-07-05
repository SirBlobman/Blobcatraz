package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class CommandFindOrigin implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("findorigin"))
		{
			Plugin plugin = Blobcatraz.instance;
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("delete"))
				{
					for(Hologram h : HologramsAPI.getHolograms(plugin))
					{
						deleteOld(h);
					}
					
					p.sendMessage(Util.blobcatraz + "Deleted any unused holograms");
					return true;
				}
				
				Location l = new Location(p.getWorld(), 0, 64, 0);
				Hologram h = HologramsAPI.createHologram(plugin, l);
				
				h.appendTextLine(Util.blobcatraz + "This is the Origin");
				h.insertTextLine(1, "It is located at x = 0, y = 64, and z = 0");
				h.appendItemLine(new ItemStack(Material.BEDROCK));
				
				p.teleport(l);
				return true;
			}
		}
		return false;
	}
	
	private void deleteOld(Hologram h)
	{
		h.delete();
	}
}