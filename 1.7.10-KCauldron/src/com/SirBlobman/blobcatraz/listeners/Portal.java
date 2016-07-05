package com.SirBlobman.blobcatraz.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.command.CommandPortal;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.Items;

public class Portal implements Listener 
{
	@EventHandler
	public void onPlayerUseWand(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Action a = e.getAction();
		ItemStack wand = Items.portalWand();
		
		if(!p.hasPermission("blobcatrz.wand.use")) return;
		if(!p.getItemInHand().equals(wand)) return;
		
		if(a.equals(Action.LEFT_CLICK_BLOCK))
		{
			Location l = e.getClickedBlock().getLocation();
			CommandPortal.p1.put(uuid, l);
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			String coords = x + " " + y + " " + z;
			
			p.sendMessage(Util.blobcatraz + "Position 1 set to §5" + coords + " §r!");
		}
		if(a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			Location l = e.getClickedBlock().getLocation();
			CommandPortal.p2.put(uuid, l);
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			String coords = x + " " + y + " " + z;
			
			p.sendMessage(Util.blobcatraz + "Position 2 set to §5" + coords + " §r!");
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerEnterPortal(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Location l = p.getLocation();
		String worldname = l.getWorld().getName();
		ConfigPortals.loadPortals();
		
		for(String portal : ConfigPortals.portals)
		{
			Double x1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.x");
			Double y1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.y");
			Double z1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.z");
			Double x2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.x");
			Double y2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.y");
			Double z2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.z");
			
			if(worldname.equals(ConfigPortals.portalConfig.getString(portal + ".pos1.world")) && worldname.equals(ConfigPortals.portalConfig.getString(portal + ".pos2.world")))
			{
				if ((l.getX() > Math.min(x1, x2))
				&& (l.getX() < Math.max(x1, x2) + 1.0D)
				&& (l.getY() >= Math.min(y1, y2))
				&& (l.getY() <= Math.max(y1, y2))
				&& (l.getZ() > Math.min(z1, z2))
				&& (l.getZ() < Math.max(z1, z2) + 1.0D))
				{
					if(ConfigPortals.portalConfig.contains(portal + ".command"))
					{
						if(!ConfigPortals.inPortal.contains(uuid))
						{
							p.chat(ConfigPortals.portalConfig.getString(portal + ".command"));
							ConfigPortals.inPortal.add(uuid);
						}
						return;
					}
					
					Location tpLocation = new Location(null, 0, 0, 0);
					tpLocation.setWorld(Bukkit.getServer().getWorld(ConfigPortals.portalConfig.getString(portal + "pos3.world")));
					tpLocation.setX(ConfigPortals.portalConfig.getDouble(portal + ".pos3.x"));
					tpLocation.setY(ConfigPortals.portalConfig.getDouble(portal + ".pos3.y"));
					tpLocation.setZ(ConfigPortals.portalConfig.getDouble(portal + ".pos3.z"));
					tpLocation.setPitch((float) ConfigPortals.portalConfig.getDouble(portal + ".pos3.pitch"));
					tpLocation.setYaw((float) ConfigPortals.portalConfig.getDouble(portal + ".pos3.yaw"));
					p.teleport(tpLocation);
				}
				else if(ConfigPortals.inPortal.contains(uuid))
				{
					ConfigPortals.inPortal.remove(uuid);
				}
			}
		}
	}
}