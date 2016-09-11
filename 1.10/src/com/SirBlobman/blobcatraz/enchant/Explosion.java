package com.SirBlobman.blobcatraz.enchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.WorldGuardChecker;
import com.SirBlobman.blobcatraz.enchant.event.RightClickEnchantEvent;

public class Explosion implements Listener
{
	static List<Player> cooldown = new ArrayList<Player>();
	
	public static final String explode = "§4§lExplosive ";
	private final String explode1 = explode + "§fI";
	private final String explode2 = explode1 + "I";
	private final String explode3 = explode2 + "I";
	
	@EventHandler
	public void explode(RightClickEnchantEvent e)
	{
		Player p = e.getPlayer();
		List<String> lore = e.getLore();
		Block b = e.getBlock();
		Location l = b.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		World w = l.getWorld();
		if(lore.contains(explode1))
		{
			if(cooldown.contains(p)) {e.setCancelled(true); p.sendMessage(Util.blobcatraz + "You must wait before using Explosion again!"); return;}
			w.createExplosion(x, y, z, 2F, false, WorldGuardChecker.canBreak(p, b));
			cooldown.add(p);
			Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Cooldown(p), 60 * 20L);
		}
		if(lore.contains(explode2))
		{
			if(cooldown.contains(p)) {e.setCancelled(true); p.sendMessage(Util.blobcatraz + "You must wait before using Explosion again!"); return;}
			w.createExplosion(x, y, z, 4F, false, WorldGuardChecker.canBreak(p, b));
			cooldown.add(p);
			Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Cooldown(p), 45 * 20L);
		}
		if(lore.contains(explode3))
		{
			if(cooldown.contains(p)) {e.setCancelled(true); p.sendMessage(Util.blobcatraz + "You must wait before using Explosion again!"); return;}
			w.createExplosion(x, y, z, 8F, false, WorldGuardChecker.canBreak(p, b));
			cooldown.add(p);
			Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Cooldown(p), 30 * 20L);
		}
	}
	
	private class Cooldown implements Runnable
	{
		Player p;
		
		Cooldown(Player p)
		{
			this.p = p;
		}
		
		@Override
		public void run()
		{
			cooldown.remove(p);
			p.sendMessage(Util.blobcatraz + "You can now use Explosion again!");
		}
	}
}