package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.enchant.event.InteractEntityEnchantEvent;

public class InstaKill implements Listener
{
	public static final String kill = "§7Insta-Kill ";
	private final String kill1 = kill + "I";
	
	@EventHandler
	public void instakill(InteractEntityEnchantEvent e)
	{
		Entity ent = e.getEntity();
		List<String> lore = e.getLore();
		if(lore.contains(kill1))
		{
			kill(ent);
		}
	}
	
	public static void kill(Entity e)
	{
		if(e instanceof LivingEntity)
		{
			LivingEntity el = (LivingEntity) e;
			el.setHealth(0.0);
		}
		else
		{
			e.remove();
		}
	}
}