package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.InteractEnchantEvent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
	
	private void kill(Entity e)
	{
		if(e instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) e;
			le.setHealth(0);
			return;
		}
		else
		{
			e.remove();
		}
	}
}