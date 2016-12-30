package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WorldUtil;

public class CommandWorld implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("world"))
		{
			if(cs instanceof Entity)
			{
				Entity e = (Entity) cs;
				String permission = "blobcatraz.world";
				if(!PlayerUtil.hasPermission(e, permission)) return true;
				
				if(args.length > 0)
				{
					String world = args[0];
					World w = Bukkit.getWorld(world);
					if(w == null)
					{
						String error = Util.prefix + Util.option("error.world.does not exist", world);
						e.sendMessage(error);
						return true;
					}
					
					Location spawn = ConfigSpawn.spawn(w);
					e.teleport(spawn);
					
					String msg = Util.prefix + Util.option("command.world.success", world);
					e.sendMessage(msg);
					return true;
				}
				cs.sendMessage(Util.NEA);
				return false;
			}
			cs.sendMessage(Util.notLiving);
			return true;
		}
		if(cmd.equals("worldlist"))
		{
			String s = WorldUtil.joinWorlds();
			cs.sendMessage("Worlds:");
			cs.sendMessage(s);
			return true;
		}
		return false;
	}
}