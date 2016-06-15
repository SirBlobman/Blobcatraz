package com.SirBlobman.blobcatraz.listeners;

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
import com.SirBlobman.blobcatraz.command.Portal;
import com.SirBlobman.blobcatraz.item.Items;

@SuppressWarnings("deprecation")
public class InPortal implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		ItemStack stick = Items.portalWand();

		if (!e.getPlayer().hasPermission("blobcatraz.wand.use")) 
		{
			return;
		}
		if (!e.getPlayer().getItemInHand().equals(stick)) 
		{
			return;
		}
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
		{
			Portal.pos1.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation());

			Location l = e.getClickedBlock().getLocation();
			int x1 = l.getBlockX();
			int y1 = l.getBlockY();
			int z1 = l.getBlockZ();
			String coords1 = x1 + " " + y1 + " " + z1;

			e.getPlayer().sendMessage(Util.blobcatraz + "Position 1 set to §5" + coords1 + " §r!");
		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
		{
			Portal.pos2.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation());

			Location l = e.getClickedBlock().getLocation();
			int x2 = l.getBlockX();
			int y2 = l.getBlockY();
			int z2 = l.getBlockZ();
			String coords2 = x2 + " " + y2 + " " + z2;

			e.getPlayer().sendMessage(Util.blobcatraz + "Position 2 set to §5" + coords2 + " §r!");
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		Location l = p.getLocation();
		for (String x : Portal.portals) 
		{
			Double x1 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos1.x")));
			Double y1 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos1.y")));
			Double z1 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos1.z")));
			Double x2 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos2.x")));
			Double y2 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos2.y")));
			Double z2 = Double.valueOf(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos2.z")));
			if (l.getWorld().getName().equals(Portal.getPortalConfig().getString(x + ".pos1.world"))) 
			{
				if (l.getWorld().getName().equals(Portal.getPortalConfig().getString(x + ".pos2.world"))) 
				{
					if ((l.getX() > Math.min(x1.doubleValue(), x2.doubleValue()))
							&& (l.getX() < Math.max(x1.doubleValue(), x2.doubleValue()) + 1.0D)
							&& (l.getY() >= Math.min(y1.doubleValue(), y2.doubleValue()))
							&& (l.getY() <= Math.max(y1.doubleValue(), y2.doubleValue()))
							&& (l.getZ() > Math.min(z1.doubleValue(), z2.doubleValue()))
							&& (l.getZ() < Math.max(z1.doubleValue(), z2.doubleValue()) + 1.0D)) 
					{
						if (Portal.getPortalConfig().contains(x + ".command")) 
						{
							if (!Portal.inPortal.contains(p.getUniqueId())) 
							{
								p.chat(Portal.getPortalConfig().getString(x + ".command"));
								Portal.inPortal.add(p.getUniqueId());
							}
							return;
						}
						Location pos3 = new Location(null, 0.0D, 0.0D, 0.0D);
						pos3.setWorld(Bukkit.getServer().getWorld(Portal.getPortalConfig().getString(x + ".pos3.world")));
						pos3.setX(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos3.x")));
						pos3.setY(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos3.y")));
						pos3.setZ(Double.parseDouble(Portal.getPortalConfig().getString(x + ".pos3.z")));
						pos3.setPitch(Float.parseFloat(Portal.getPortalConfig().getString(x + ".pos3.pitch")));
						pos3.setYaw(Float.parseFloat(Portal.getPortalConfig().getString(x + ".pos3.yaw")));
						p.teleport(pos3);
					} 
					else if (Portal.inPortal.contains(p.getUniqueId())) 
					{
						Portal.inPortal.remove(p.getUniqueId());
					}
				}
			}
		}
	}
}