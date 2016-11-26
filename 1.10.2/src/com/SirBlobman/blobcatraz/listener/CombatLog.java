package com.SirBlobman.blobcatraz.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.scoreboard.ScoreBoardManager;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WorldGuardUtil;
import com.google.common.collect.Maps;

public class CombatLog implements Listener
{
	public static HashMap<Player, Long> tagged = Maps.newHashMap();
	
	public static void start()
	{
		Bukkit.getServer().getScheduler().runTaskTimer(Blobcatraz.instance, new Runnable()
		{
			@Override
			public void run()
			{
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(tagged.containsKey(p))
					{
						long time = System.currentTimeMillis();
						long escape = tagged.get(p);
						int seconds = (int) ((escape - time) / 1000);
						if(seconds <= 0) 
						{
							p.sendMessage(Util.blobcatraz + Util.message("player.outOfCombat"));
							tagged.remove(p);
							ScoreBoardManager.clear(p);
						}
						else
						{
							p.setScoreboard(ScoreBoardManager.setCombat(p, seconds));
						}
					}
				}
			}
		}, 20L, 20L);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void pvp(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if(damager instanceof Projectile)
		{
			Projectile pj = (Projectile) damager;
			ProjectileSource pjs = pj.getShooter();
			if(pjs instanceof Entity) damager = (Entity) pjs;
		}
		
		if(!(damager instanceof Player) || !(damaged instanceof Player)) return;
		
		Player ded = (Player) damaged;
		Player der = (Player) damager;
		try{if(WorldGuardUtil.canPvP(der)) return;} catch(Exception | Error ex) {}
		long time = System.currentTimeMillis();
		long escape = time + (ConfigBlobcatraz.load().getLong("combat log.seconds") * 1000L);

		if(!tagged.containsKey(der)) damager.sendMessage(Util.blobcatraz + Util.message("player.attacker", damaged.getName()));
		if(!tagged.containsKey(ded)) damaged.sendMessage(Util.blobcatraz + Util.message("player.attacked", damager.getName()));
		tagged.put(der, escape);
		tagged.put(ded, escape);
	}
	
	@EventHandler
	public void death(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(tagged.containsKey(p)) p.sendMessage(Util.blobcatraz + Util.message("player.outOfCombat"));
		tagged.remove(p);
		p.setScoreboard(ScoreBoardManager.setCombat(p, 0));
	}
}