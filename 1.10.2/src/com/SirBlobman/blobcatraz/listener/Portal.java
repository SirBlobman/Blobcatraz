package com.SirBlobman.blobcatraz.listener;

import java.util.UUID;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigPortals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class Portal implements Listener
{
	FileConfiguration config = ConfigBlobcatraz.load();
	FileConfiguration portals = ConfigPortals.load();
	
	@EventHandler
	public void netherEnd(PlayerPortalEvent e)
	{
		Player p = e.getPlayer();
		World w = p.getWorld();
		String world = w.getName();
		for(String s : config.getStringList("portals.no other portal worlds"))
		{
			if(world.equals(s)) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void teleport(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Location l = p.getLocation();
		World w = p.getWorld();
		String world = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		
		for(String portal : ConfigPortals.getPortals())
		{
			String world1 = portals.getString(portal + ".pos1.world");
			double x1 = portals.getDouble(portal + ".pos1.x");
			double y1 = portals.getDouble(portal + ".pos1.y");
			double z1 = portals.getDouble(portal + ".pos1.z");
			String world2 = portals.getString(portal + ".pos2.world");
			double x2 = portals.getDouble(portal + ".pos2.x");
			double y2 = portals.getDouble(portal + ".pos2.y");
			double z2 = portals.getDouble(portal + ".pos2.z");
			
			if(world.equals(world1) && world.equals(world2))
			{
				if
				(
					x > Math.min(x1, x2) &&
					x < (Math.max(x1, x2) + 1.0D) &&
					y >= Math.min(y1, y2) &&
					y <= Math.max(y1, y2) &&
					z > Math.min(z1, z2) &&
					z < (Math.max(z1, z2) + 1.0D)
				)
				{
					if(portals.contains(portal + ".command"))
					{
						if(!ConfigPortals.getPeopleInPortal().contains(uuid))
						{
							String command = portals.getString(portal + ".command");
							p.chat(command);
							ConfigPortals.getPeopleInPortal().add(uuid);
						}
						return;
					}
					
					Location tp = new Location(null, 0, 0, 0);
					String world3 = portals.getString(portal + ".pos3.world");
					
					World w3 = Bukkit.getWorld(world3);
					double x3 = portals.getDouble(portal + ".pos3.x");
					double y3 = portals.getDouble(portal + ".pos3.y");
					double z3 = portals.getDouble(portal + ".pos3.z");
					float pitch = (float) portals.getDouble(portal + ".pos3.pitch");
					float yaw = (float) portals.getDouble(portal + ".pos3.yaw");
					
					tp.setWorld(w3);
					tp.setX(x3);
					tp.setY(y3);
					tp.setZ(z3);
					tp.setPitch(pitch);
					tp.setYaw(yaw);
					
					p.teleport(tp);
					p.playSound(l, Sound.BLOCK_PORTAL_TRAVEL, 100.0F, 1.0F);
				}
				else if(ConfigPortals.getPeopleInPortal().contains(uuid)) ConfigPortals.getPeopleInPortal().remove(uuid);
			}
		}
	}
}