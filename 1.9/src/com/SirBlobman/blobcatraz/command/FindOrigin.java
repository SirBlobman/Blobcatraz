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

public class FindOrigin implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("findorigin")) 
		{
			Plugin pl = Blobcatraz.instance;
			
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("delete"))
				{
					for (Hologram h : HologramsAPI.getHolograms(pl)) 
					{
						deleteOld(h);
					}
					p.sendMessage(Util.blobcatraz + "Deleted any unused holograms");
					return true;
				}
			}
			
			
			for (Hologram h : HologramsAPI.getHolograms(pl)) 
			{
				deleteOld(h);
			}
			Location w = new Location(p.getWorld(), 0.0, 64.0, 0.0);
			Hologram h = HologramsAPI.createHologram(pl, w);
			
			h.appendTextLine(Util.blobcatraz + "This is the Origin");
			h.insertTextLine(1, "It is located at x = 0, y = 64, and z = 0");
			h.appendItemLine(new ItemStack(Material.BARRIER));
			
			p.teleport(w);
			return true;
		}
		
		return false;
	}

	private void deleteOld(Hologram h) 
	{
		h.delete();
	}
}
