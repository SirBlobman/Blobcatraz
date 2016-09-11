package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class CommandSpawn implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			Location l = le.getLocation();
			World w = l.getWorld();
			String command = c.getName().toString();
			
			if(command.equals("spawn"))
			{
				ConfigSpawn.spawn(le, w);
				return true;
			}
			if(command.equals("setspawn"))
			{
				String permission = "blobcatraz.setspawn";
				if(!PlayerUtil.hasPermission(le, permission)) return true;
				
				ConfigSpawn.setSpawn(l);
				Location spawn = ConfigSpawn.getSpawn(w);
				String coords = (int) spawn.getX() + " " + (int) spawn.getY() + " " + (int) spawn.getZ();
				le.sendMessage(Util.blobcatraz + "Set the spawn to §5" + coords);
				
				return true;
			}
			if(command.equals("hub"))
			{
				ConfigSpawn.hub(le);
				return true;
			}
			if(command.equals("sethub"))
			{
				String permission = "blobcatraz.setspawn";
				if(!PlayerUtil.hasPermission(le, permission)) return true;
				
				ConfigSpawn.setHub(l);
				Location spawn = ConfigSpawn.getHub();
				String coords = (int) spawn.getX() + " " + (int) spawn.getY() + " " + (int) spawn.getZ();
				le.sendMessage(Util.blobcatraz + "Set the HUB to §5" + coords);
				
				return true;
			}
		}
		cs.sendMessage(Util.csNotLiving);
		return true;
	}
}