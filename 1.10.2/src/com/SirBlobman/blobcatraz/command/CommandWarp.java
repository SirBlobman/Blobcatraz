package com.SirBlobman.blobcatraz.command;

import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class CommandWarp implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("warp"))
			{
				String permission = "blobcatraz.warp";
				if(!PlayerUtil.hasPermission(le, permission)) return true;
				if(args.length == 0)
				{
					String permission2 = "blobcatraz.warps";
					if(!PlayerUtil.hasPermission(le, permission2)) return true;
					
					StringBuffer list = new StringBuffer();
					List<String> warps = ConfigWarps.getWarps();
					for(int i = 0; i < warps.size(); i++)
					{
						String warp = warps.get(i);
						String permission3 = "blobcatraz.warps." + warp;
						if(le.hasPermission(permission3))
						{
							if(i != 0) list.append(Util.format("&r, "));
							list.append("§2" + warp);
						}
					}
					cs.sendMessage(Util.blobcatraz + "List of warps:");
					cs.sendMessage(list.toString());
					return true;
				}
				
				String warp = Util.getFinal(args, 0);
				if(ConfigWarps.exists(warp))
				{
					String permission4 = "blobcatraz.warps." + warp;
					if(!PlayerUtil.hasPermission(le, permission4)) return true;
					
					TeleportUtil.warp(le, warp);
					le.sendMessage(Util.blobcatraz + "Warped to §c" + warp);
					return true;
				}
				le.sendMessage(Util.blobcatraz + "Warp doesn't exist: §5" + warp);
				return true;
			}
			if(command.equals("setwarp"))
			{
				if(args.length > 0)
				{
					String permission = "blobcatraz.setwarp";
					if(!PlayerUtil.hasPermission(le, permission)) return true;
					
					String name = Util.getFinal(args, 0);
					Location warp = le.getLocation();
					World w = warp.getWorld();
					String world = w.getName();
					int x = warp.getBlockX();
					int y = warp.getBlockY();
					int z = warp.getBlockZ();
					String swarp = "§6" + world + " " + x + " " + y + " " + z;
					ConfigWarps.save(name, warp);
					le.sendMessage(Util.blobcatraz + "Set warp §2" + name + " §rto " + swarp);
					return true;
				}
				le.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("delwarp"))
			{
				if(args.length > 0)
				{
					String permission = "blobcatraz.delwarp";
					if(!PlayerUtil.hasPermission(le, permission)) return true;
					
					String name = Util.getFinal(args, 0);
					ConfigWarps.delete(name);
					le.sendMessage(Util.blobcatraz + "Deleted warp §2" + name);

					return true;
				}
				le.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.csNotLiving);
		return true;
	}
}