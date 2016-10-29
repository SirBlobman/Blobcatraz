package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class ThunderStrike implements Listener
{
	public static final String TS = Enchant.STRIKE.getName();
	private final String ts1 = TS + " §fI";
	private final String ts2 = ts1 + "I";
	private final String ts3 = ts2 + "I";
	
	@EventHandler
	public void ts(DamageEnchantEvent e)
	{
		LivingEntity ded = e.getDamaged();
		List<String> lore = e.getLore();
		Location l = ded.getLocation();
		
		double chance = Math.random();
		if(lore.contains(ts1) && chance <= 0.2) strike(l);
		if(lore.contains(ts2) && chance <= 0.35) strike(l);
		if(lore.contains(ts3) && chance <= 0.45) strike(l);
	}
	
	private void strike(Location l)
	{
		World w = l.getWorld();
		w.strikeLightning(l);
	}
}