package com.SirBlobman.blobcatraz.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigPortals;

public class ListenPortal implements Listener
{
	private static YamlConfiguration config = ConfigBlobcatraz.load();
	private static List<UUID> in = new ArrayList<UUID>();
	
	@EventHandler
	public void non(PlayerPortalEvent e)
	{
		Player p = e.getPlayer();
		World world = p.getWorld();
		String w = world.getName();
		List<String> worlds = config.getStringList("portals.only blobcatraz portals");
		for(String s : worlds)
		{
			boolean b1 = w.equals(s);
			if(b1) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void tp(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		Location l = p.getLocation();
		World world = p.getWorld();
		String w = world.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		
		YamlConfiguration portalc = ConfigPortals.load();
		List<String> portals = ConfigPortals.portals();
		for(String portal : portals)
		{
			String path1 = portal + ".pos1.";
			String w1 = portalc.getString(path1 + "world");
			double x1 = portalc.getDouble(path1 + "x");
			double y1 = portalc.getDouble(path1 + "y");
			double z1 = portalc.getDouble(path1 + "z");
			
			String path2 = portal + ".pos2.";
			String w2 = portalc.getString(path2 + "world");
			double x2 = portalc.getDouble(path2 + "x");
			double y2 = portalc.getDouble(path2 + "y");
			double z2 = portalc.getDouble(path2 + "z");
			
			boolean b1 = w.equals(w1);
			boolean b2 = w.equals(w2);
			boolean b3 = (b1 && b2);
			if(b3)
			{
				boolean bx1 = (x > Math.min(x1, x2));
				boolean bx2 = (x < (Math.max(x1, x2) + 1.0D));
				boolean by1 = (y >= Math.min(y1, y2));
				boolean by2 = (y >= Math.max(y1, y2));
				boolean bz1 = (z > Math.min(z1, z2));
				boolean bz2 = (z < (Math.max(z1, z2) + 1.0D));
				boolean b4 = (bx1 && bx2 && by1 && by2 && bz1 && bz2);
				if(b4)
				{
					if(portalc.contains(portal + ".command"))
					{
						if(!in.contains(uuid))
						{
							String cmd = portalc.getString(portal + ".command");
							p.chat(cmd);
							in.add(uuid);
						}
						return;
					}
					
					Location tp = new Location(null, 0, 0, 0);
					
					String path3 = portal + ".pos3.";
					String w3 = portalc.getString(path3 + "world");
					World world3 = Bukkit.getWorld(w3);
					double x3 = portalc.getDouble(path3 + "x");
					double y3 = portalc.getDouble(path3 + "y");
					double z3 = portalc.getDouble(path3 + "z");
					float pitch = (float) portalc.getDouble(path3 + "pitch");
					float yaw = (float) portalc.getDouble(path3 + "yaw");
					
					tp.setWorld(world3);
					tp.setX(x3);
					tp.setY(y3);
					tp.setZ(z3);
					tp.setPitch(pitch);
					tp.setYaw(yaw);
					
					p.teleport(tp);
					p.playSound(l, Sound.BLOCK_PORTAL_TRAVEL, 100.0F, 1.0F);
				}
				else if(in.contains(uuid)) in.remove(uuid);
			}
		}
	}
}