package com.SirBlobman.blobcatraz.listener;

import java.util.HashMap;

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
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.WorldGuardChecker;
import com.SirBlobman.blobcatraz.scoreboard.CombatTimer;

public class CombatLog implements Listener 
{
	CombatTimer CT = new CombatTimer(Blobcatraz.instance);
	public static HashMap<Player, Long> tagged = new HashMap<Player, Long>();
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void pvp(EntityDamageByEntityEvent e)
	{
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		
		if(damager instanceof Projectile)
		{
			Projectile pj = (Projectile) damager;
			ProjectileSource pjs = pj.getShooter();
			Entity ent = null;
			if(pjs instanceof Entity) ent = (Entity) pjs;
			damager = ent;
		}
		
		if(!(damager instanceof Player) || !(damaged instanceof Player)) return;
		
		Player p = (Player) damager;
		if(!WorldGuardChecker.canPvP(p)) return;
		long time = System.currentTimeMillis();
		long escape = time + Blobcatraz.millis;
		
		if(!tagged.containsKey(p))
		{
			tagged.put(p, escape);
			damager.sendMessage(Util.blobcatraz + "You are now in combat for attacking §5" + damaged.getName() + "§r.");
		}
		
		if(!tagged.containsKey(p))
		{
			tagged.put(p, escape);
			damaged.sendMessage(Util.blobcatraz + "§5" + damager.getName() + " §rattacked you! You are now in combat.");
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(tagged.containsKey(p))
		{
			tagged.remove(p);
			CT.clearTimer(p);
			p.sendMessage(Util.blobcatraz + "You are no longer in combat!");
		}
	}
}