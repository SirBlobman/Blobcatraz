package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSpawn implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("spawn")) return spawn(cs);
		if(cmd.equals("setspawn")) return setspawn(cs);
		return false;
	}
	
	private boolean spawn(CommandSender cs)
	{
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			World w = le.getWorld();
			Location spawn = ConfigSpawn.spawn(w);
			le.teleport(spawn);
			String msg = Util.prefix + Util.option("command.spawn.success", w.getName());
			le.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.notLiving);
		return true;
	}
	
	private boolean setspawn(CommandSender cs)
	{
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			String permission = "blobcatraz.setspawn";
			if(!PlayerUtil.hasPermission(le, permission)) return true;
			
			Location l = le.getLocation();
			ConfigSpawn.setSpawn(l);
			
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			String coords = String.format("&5%s %s %s", x, y, z);
			String msg = Util.option("command.setspawn.success", l.getWorld().getName(), coords);
			le.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.notLiving);
		return true;
	}
}