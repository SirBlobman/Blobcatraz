package com.SirBlobman.blobcatraz.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigWarps;

public class CommandWarp implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof LivingEntity)) {cs.sendMessage(Util.commandExecutorNonLiving); return true;}
		LivingEntity le = (LivingEntity) cs;
		
		if(label.equalsIgnoreCase("warp"))
		{
			if(!le.hasPermission("blobcatraz.warp")) {le.sendMessage(Util.noPermission + "blobcatraz.warp"); return true;}
			if(args.length == 0) 
			{
				if(le.hasPermission("blobcatraz.warps"))
				{
					StringBuffer warpList = new StringBuffer();
					for(int i = 0; i < ConfigWarps.getWarps().size(); i++)
					{
						if(i != 0) warpList.append(Util.color("&r, "));
						if(le.hasPermission("blobcatraz.warps." + ConfigWarps.getWarps().get(i))) warpList.append("§2" + ConfigWarps.getWarps().get(i));
					}
					cs.sendMessage(Util.blobcatraz + "List of warps: ");
					cs.sendMessage(warpList.toString());
					return true;
				}
				else
				{
					le.sendMessage(Util.noPermission + "blobcatraz.warps");
					return true;
				}
			}
			String warpName = Util.getFinalArg(args, 0);
			if(ConfigWarps.doesWarpExist(warpName))
			{
				if(le.hasPermission("blobcatraz.warps." + warpName))
				{
					ConfigWarps.teleportToWarp(le, warpName);
					le.sendMessage(Util.blobcatraz + "Warped to " + warpName);
					return true;
				}
				else
				{
					le.sendMessage(Util.noPermission + "blobcatraz.warps." + warpName);
					return true;
				}
			}
			else
			{
				le.sendMessage(Util.blobcatraz + "That warp doesn't exist");
				return true;
			}
		}
		if(label.equalsIgnoreCase("setwarp"))
		{
			if(args.length < 1)
			{
				le.sendMessage(Util.notEnoughArguments);
				return false;
			}
			String warpName = Util.getFinalArg(args, 0);
			if(le.hasPermission("blobcatraz.setwarp"))
			{
				Location l = le.getLocation();
				String sl = l.getWorld() + " " + l.getX() + " " + l.getY() + " " + l.getZ();
				ConfigWarps.saveWarp(warpName, l);
				le.sendMessage(Util.blobcatraz + "Set warp §2" + warpName + " §r to §6" + sl);
				return true;
			}
			else
			{
				le.sendMessage(Util.noPermission + "blobcatraz.setwarp");
				return true;
			}
		}
		if(label.equalsIgnoreCase("delwarp"))
		{
			if(args.length < 1)
			{
				le.sendMessage(Util.notEnoughArguments);
				return false;
			}
			String warpName = Util.getFinalArg(args, 0);
			
			if(le.hasPermission("blobcatraz.delwarp"))
			{
				ConfigWarps.deleteWarp(warpName);
				le.sendMessage(Util.blobcatraz + "Deleted warp §2" + warpName);
				return true;
			}
			else
			{
				le.sendMessage(Util.noPermission + "blobcatraz.delwarp");
				return true;
			}
		}
		return false;
	}
}