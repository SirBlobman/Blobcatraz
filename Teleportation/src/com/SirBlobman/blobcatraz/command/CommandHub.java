package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import com.SirBlobman.blobcatraz.config.ConfigHub;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandHub implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Entity)
		{
			Entity e = (Entity) cs;
			String cmd = c.getName().toLowerCase();
			
			if(cmd.equals("hub"))
			{
				Location hub = ConfigHub.hub();
				e.teleport(hub);
				String msg = Util.prefix + Util.option("command.hub.success");
				e.sendMessage(msg);
				return true;
			}
			if(cmd.equals("sethub"))
			{
				String permission = "blobcatraz.sethub";
				if(!PlayerUtil.hasPermission(e, permission)) return true;
				
				Location l = e.getLocation();
				World w = l.getWorld();
				String world = w.getName();
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				String coords = String.format("§5%s %s %s %s§r", world, x, y, z);
				
				ConfigHub.setHub(l);
				String msg = Util.prefix + Util.option("command.sethub", coords);
				e.sendMessage(msg);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.notLiving);
		return true;
	}
}