package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Cure implements Listener
{
	private final String success = Util.blobcatraz + Util.message("enchant.cure.success");
	private final String failure = Util.blobcatraz + Util.message("enchant.cure.failure");
	
	public static final String CURE = Enchant.HEAL_ZOMBIE.getName();
	private final String cure1 = CURE + " §fI";
	private final String cure2 = cure1 + "I";
	private final String cure3 = cure2 + "I";
	private final String cure4 = cure1 + "V";
	
	@EventHandler
	public void cure(DamageEnchantEvent e)
	{
		LivingEntity damager = e.getDamager();
		LivingEntity damaged = e.getDamaged();
		List<String> lore = e.getLore();
		
		if(damaged instanceof Zombie)
		{
			Zombie z = (Zombie) damaged;
			@SuppressWarnings("deprecation")
			boolean v = z.isVillager();
			if(v)
			{
				double chance = Math.random();
				if(lore.contains(cure1))
				{
					if(chance < 0.25D)
					{
						success(z, damager);
						return;
					}
					else
					{
						fail(damager);
						return;
					}
				}
				if(lore.contains(cure2))
				{
					if(chance < 0.5D)
					{
						success(z, damager);
						return;
					}
					else
					{
						fail(damager);
						return;
					}
				}
				if(lore.contains(cure3))
				{
					if(chance < 0.75D)
					{
						success(z, damager);
						return;
					}
					else
					{
						fail(damager);
						return;
					}					
				}
				if(lore.contains(cure4))
				{
					success(z, damager);
					return;
				}
			}
		}
	}
	
	private void success(Zombie z, LivingEntity der)
	{
		World w = z.getWorld();
		Location l = z.getLocation();
		z.remove();
		w.spawnEntity(l, EntityType.VILLAGER);
		der.sendMessage(success);
	}
	
	private void fail(LivingEntity der)
	{
		der.sendMessage(failure);
	}
}