package com.SirBlobman.blobcatraz.enchant;

import java.util.ArrayList;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.enchant.event.ClickEnchantEvent;
import com.SirBlobman.blobcatraz.enchant.event.ClickEnchantEvent.ClickType;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WorldGuardUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Explosion implements Listener
{
	private static List<Player> cool = new ArrayList<Player>();
	
	public static final String EXPLODE = Enchant.EXPLODE.getName();
	private final String explode1 = EXPLODE + " §fI";
	private final String explode2 = explode1 + "I";
	private final String explode3 = explode2 + "I";
	
	@EventHandler
	public void explode(ClickEnchantEvent e)
	{
		List<String> lore = e.getLore();
		Player p = e.getPlayer();
		Block b = e.getBlock();
		World w = b.getWorld();
		ClickType ct = e.getClick();
		Location l = b.getLocation();

		if(lore.contains(explode1) && ct == ClickType.RIGHT)
		{
			if(cool.contains(p))
			{
				String msg = Util.blobcatraz + Util.message("enchant.explosion.cooldown.wait");
				p.sendMessage(msg);
				return;
			}
			explode(p, w, l, 1);
		}
		if(lore.contains(explode2) && ct == ClickType.RIGHT)
		{
			if(cool.contains(p))
			{
				String msg = Util.blobcatraz + Util.message("enchant.explosion.cooldown.wait");
				p.sendMessage(msg);
				return;
			}
			explode(p, w, l, 2);
		}
		if(lore.contains(explode3) && ct == ClickType.RIGHT)
		{
			if(cool.contains(p))
			{
				String msg = Util.blobcatraz + Util.message("enchant.explosion.cooldown.wait");
				p.sendMessage(msg);
				return;
			}
			explode(p, w, l, 3);
		}
	}
	
	private void explode(Player p, World w, Location l, int level)
	{
		int cooldown = 0;
		float power = 0.0F;
		switch(level)
		{
		case 1:
			cooldown = 60;
			power = 2.0F;
			break;
		case 2:
			cooldown = 45;
			power = 4.0F;
			break;
		case 3:
			cooldown = 30;
			power = 8.0F;
			break;
		}
		
		boolean can = true;
		try{can = WorldGuardUtil.canBreak(p, l.getBlock());} catch(Throwable ex) {return;}
		w.createExplosion(l.getX(), l.getY(), l.getZ(), power, false, can);
		cool.add(p);
		Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Cooldown(p), cooldown * 20L);
	}
	
	private class Cooldown implements Runnable
	{
		Player player;
		Cooldown(Player p) {this.player = p;}
		
		@Override
		public void run()
		{
			cool.remove(player);
			String msg = Util.blobcatraz + Util.message("enchant.explosion.cooldown.end");
			player.sendMessage(msg);
		}
	}
}