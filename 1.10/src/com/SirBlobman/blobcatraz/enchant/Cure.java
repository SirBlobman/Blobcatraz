package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class Cure implements Listener
{
	String success = Util.blobcatraz + "Cured the Villager!";
	String failure = Util.blobcatraz + "§4Failed to cure the Zombie!";
	
	public static final String cure = "§7Cure ";
	private final String cure1 = cure + "I";
	private final String cure2 = cure1 + "I";
	private final String cure3 = cure2 + "I";
	private final String cure4 = cure1 + "V";
	
	@EventHandler
	public void cure(DamageEnchantEvent e)
	{
		LivingEntity damager = e.getDamager();
		LivingEntity damaged = e.getDamaged();
		List<String> lore = e.getLore();
		if(!(damaged instanceof Zombie)) return;
		Zombie z = (Zombie) damaged;
		if(!z.isVillager()) return;
		double chance = Math.random();
		if(lore.contains(cure1))
		{
			if(chance < .25)
			{
				Location l = z.getLocation();
				World w = l.getWorld();
				z.remove();
				w.spawnEntity(l, EntityType.VILLAGER);
				damager.sendMessage(success);
				return;
			}
			else
			{
				damager.sendMessage(failure);
				return;
			}
		}
		if(lore.contains(cure2))
		{
			if(chance < .5)
			{
				Location l = z.getLocation();
				World w = l.getWorld();
				z.remove();
				w.spawnEntity(l, EntityType.VILLAGER);
				damager.sendMessage(success);
				return;
			}
			else
			{
				damager.sendMessage(failure);
				return;
			}
		}
		if(lore.contains(cure3))
		{
			if(chance < .75)
			{
				Location l = z.getLocation();
				World w = l.getWorld();
				z.remove();
				w.spawnEntity(l, EntityType.VILLAGER);
				damager.sendMessage(success);
				return;
			}
			else
			{
				damager.sendMessage(failure);
				return;
			}
		}
		if(lore.contains(cure4))
		{
			Location l = z.getLocation();
			World w = l.getWorld();
			z.remove();
			w.spawnEntity(l, EntityType.VILLAGER);
			damager.sendMessage(success);
			return;
		}
	}
}