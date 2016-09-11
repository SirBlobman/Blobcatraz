package com.SirBlobman.blobcatraz.command;

import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandFindOrigin implements CommandExecutor
{
	static List<Hologram> holograms;
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("findorigin"))
			{
				if(Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays"))
				{
					if(!PlayerUtil.hasPermission(p, "blobcatraz.findorigin")) return true;
					if(args.length == 1)
					{
						String sub = args[0].toLowerCase();
						if(sub.equals("delete"))
						{
							if(!PlayerUtil.hasPermission(p, "blobcatraz.findorigin.delete")) return true;
							deleteOld();
							p.sendMessage(Util.blobcatraz + "Unused holograms were deleted");
							return true;
						}
						p.sendMessage(Util.IA);
						return false;
					}

					World w = p.getWorld();
					Location origin = new Location(w, 0.0D, 64.0D, 0.0D);
					Hologram h = HologramsAPI.createHologram(Blobcatraz.instance, origin);
					Object[] data = new Object[]
							{
									Util.blobcatraz,
									"This is the Origin",
									"It is located at 0, 64, 0",
									new ItemStack(Material.BARRIER)
							};
					add(h, data);
					holograms.add(h);

					p.teleport(origin);
					return true;
				}
				cs.sendMessage(Util.blobcatraz + "You need HolographicDisplays to do this!");
				return true;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	public static void deleteOld()
	{
		for(Hologram h : HologramsAPI.getHolograms(Blobcatraz.instance))
		{
			if(holograms.contains(h)) delete(h);
		}
	}
	
	private static void delete(Hologram h)
	{
		holograms.remove(h);
		h.delete();
	}
	
	private void add(Hologram h, Object... itemOrString)
	{
		if(itemOrString == null) return;
		for(Object o : itemOrString)
		{
			if(o instanceof String)
			{
				String text = (String) o;
				h.appendTextLine(text);
				return;
			}
			if(o instanceof ItemStack)
			{
				ItemStack item = (ItemStack) o;
				h.appendItemLine(item);
				return;
			}
			
			String error = "Invalid object: " + o.getClass().getSimpleName() + "\nIt should be a String or an ItemStack";
			IllegalArgumentException IAE = new IllegalArgumentException(error);
			throw IAE;
		}
	}
}