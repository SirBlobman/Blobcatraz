package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;

public class CommandSpawn implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(!(cs instanceof LivingEntity)) {cs.sendMessage(Util.commandExecutorNonLiving); return true;}
		LivingEntity le = (LivingEntity) cs;
		
		if(label.equalsIgnoreCase("spawn"))
		{
			ConfigSpawn.teleportToSpawn(le);
			return true;
		}
		if(label.equalsIgnoreCase("setspawn"))
		{
			if(!cs.hasPermission("blobcatraz.setspawn") && !cs.isOp()) {cs.sendMessage(Util.noPermission + "blobcatraz.setspawn"); return true;}
			
			Location l = le.getLocation();
			String sl = l.getWorld().getName() + ": " + l.getX() + " " + l.getY() + " " + l.getZ();
			ConfigSpawn.setSpawn(l);
			le.sendMessage(Util.blobcatraz + "You set the spawn to §3" + sl + "§r!");
			return true;
		}
		return false;
	}
}