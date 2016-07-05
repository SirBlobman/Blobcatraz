package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class CommandFindOrigin implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("findorigin"))
		{
			if(args.length == 1 && args[0].equalsIgnoreCase("delete"))
			{
				deleteOld();
				p.sendMessage(Util.blobcatraz + "Deleted any unused Holograms");
				return true;
			}
			
			World w = p.getWorld();
			Location l = new Location(w, 0, 64, 0);
			Hologram h = HologramsAPI.createHologram(Blobcatraz.instance, l);
			
			h.appendTextLine(Util.blobcatraz + "This is the Origin");
			h.insertTextLine(1, "It is located at 0, 64, 0");
			h.appendItemLine(new ItemStack(Material.BARRIER));
			
			p.teleport(l);
			return true;
		}
        return false;
    }
	
	private void deleteOld()
	{
		for(Hologram h : HologramsAPI.getHolograms(Blobcatraz.instance)) h.delete();
	}
}