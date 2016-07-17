package com.SirBlobman.blobcatraz.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigPortals;

public class Portal implements Listener
{
	@EventHandler
	public void onPlayerEnterPortal(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Location l = p.getLocation();
		World w = l.getWorld();
		String worldName = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		ConfigPortals.loadPortals();
		
		for(String portal : ConfigPortals.portals)
		{
			Double x1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.x");
			Double y1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.y");
			Double z1 = ConfigPortals.portalConfig.getDouble(portal + ".pos1.z");
			Double x2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.x");
			Double y2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.y");
			Double z2 = ConfigPortals.portalConfig.getDouble(portal + ".pos2.z");
			
			if(worldName.equals(ConfigPortals.portalConfig.getString(portal + ".pos1.world")))
			{
				if(worldName.equals(ConfigPortals.portalConfig.getString(portal + ".pos2.world")))
				{
					if ((x > Math.min(x1, x2))
							&& (x < Math.max(x1, x2) + 1.0D)
							&& (y >= Math.min(y1, y2))
							&& (y <= Math.max(y1, y2))
							&& (z > Math.min(z1, z2))
							&& (z < Math.max(z1, z2) + 1.0D))
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
						
						Location pos3 = new Location(null, 0D, 0D, 0D);
						World w3 = Bukkit.getServer().getWorld(ConfigPortals.portalConfig.getString(portal + ".pos3.world"));
						double x3 = ConfigPortals.portalConfig.getDouble(portal + ".pos3.x");
						double y3 = ConfigPortals.portalConfig.getDouble(portal + ".pos3.y");
						double z3 = ConfigPortals.portalConfig.getDouble(portal + ".pos3.z");
						float pitch3 = (float) ConfigPortals.portalConfig.getDouble(portal + ".pos3.pitch");
						float yaw3 =  (float) ConfigPortals.portalConfig.getDouble(portal + ".pos3.yaw");
						
						pos3.setWorld(w3);
						pos3.setX(x3);
						pos3.setY(y3);
						pos3.setZ(z3);
						pos3.setPitch(pitch3);
						pos3.setYaw(yaw3);
						
						p.teleport(pos3);
					}
					else if(ConfigPortals.inPortal.contains(uuid)) ConfigPortals.inPortal.remove(uuid);
				}
			}
		}
	}
}