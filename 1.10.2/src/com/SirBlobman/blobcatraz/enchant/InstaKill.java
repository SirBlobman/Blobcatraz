package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.SirBlobman.blobcatraz.enchant.event.InteractEnchantEvent;

public class InstaKill implements Listener
{
	public static final String KILL = Enchant.ONE_SHOT.getName();
	private final String kill1 = KILL + " §fI";
	
	@EventHandler
	public void kill(InteractEnchantEvent e)
	{
		List<String> lore = e.getLore();
		Entity ent = e.getEntity();
		if(lore.contains(kill1)) kill(ent);
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		String name = p.getName();
		String omsg = e.getDeathMessage();
		if(omsg.contains(name + " died")) {e.setDeathMessage("");}
	}
	
	private void kill(Entity e)
	{
		if(e instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) e;
			le.setHealth(0);
			return;
		} else e.remove();
	}
}