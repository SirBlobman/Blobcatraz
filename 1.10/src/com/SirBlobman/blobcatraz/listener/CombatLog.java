package com.SirBlobman.blobcatraz.listener;

import java.util.HashMap;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.scoreboard.CombatTimer;

public class CombatLog implements Listener 
{
	CombatTimer CT = new CombatTimer(Blobcatraz.instance);
	public static HashMap<Player, Long> tagged = new HashMap<Player, Long>();
	
	@EventHandler
	public void pvp(EntityDamageByEntityEvent e)
	{
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		
		if(damager instanceof Arrow)
		{
			damager = (Player) ((Arrow) damager).getShooter();
		}
		
		Util.print(damager.getName() + " attacked " + damaged.getName());
		if(!(damager instanceof Player) || !(damaged instanceof Player)) return;
		
		long time = System.currentTimeMillis();
		long escape = time + Blobcatraz.millis;
		
		if(!tagged.containsKey((Player) damager))
		{
			tagged.put((Player) damager, escape);
			damager.sendMessage(Util.blobcatraz + "You are now in combat for attacking §5" + damaged.getName() + "§r.");
		}
		
		if(!tagged.containsKey((Player) damaged))
		{
			tagged.put((Player) damaged, escape);
			damaged.sendMessage(Util.blobcatraz + "§5" + damager.getName() + " §rattacked you! You are now in combat.");
		}
	}
	
	@EventHandler
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