package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.config.Warp;
import com.SirBlobman.blobcatraz.gui.GuiWarps;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.Util;

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
					List<String> warps = ConfigWarps.getStringWarps();
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
					ItemStack icon = new ItemStack(Material.END_BRICKS);
					ItemMeta meta = icon.getItemMeta();
					meta.setDisplayName(name);
					ConfigWarps.save(name, warp, icon);
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
			if(command.equals("warps"))
			{
				if(le instanceof Player)
				{
					Player p = (Player) le;
					if(args.length == 1)
					{
						try
						{
							int page = 0;
							page = Integer.parseInt(args[0]);
							GuiWarps gw = new GuiWarps();
							List<Warp> warps = ConfigWarps.getWarps();
							if(warps.size() == 0)
							{
								p.sendMessage(Util.blobcatraz + "There are 0 warps");
								return true;
							}
							Inventory gui = gw.warps(page);
							PlayerUtil.open(p, gui);
							return true;
						} catch(Exception ex)
						{
							le.sendMessage(Util.IA);
							return false;
						}
					}
					le.sendMessage(Util.NEA);
					return false;
				}
				le.sendMessage(Util.csNotPlayer);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotLiving);
		return true;
	}
}