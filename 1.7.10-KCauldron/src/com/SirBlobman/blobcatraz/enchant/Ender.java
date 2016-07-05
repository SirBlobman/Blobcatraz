package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Ender implements Listener 
{
	@EventHandler
	public void onShootArrow(EntityShootBowEvent e)
	{
		LivingEntity le = e.getEntity();
		EntityEquipment ee = le.getEquipment();
		ItemStack held = ee.getItemInHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(held.getType() == Material.BOW && lore.contains("§7Ender I"))
		{
			if(le instanceof Player)
			{
				Player p = (Player)le;
				
				if(p.getGameMode() != GameMode.CREATIVE)
				{
					if(p.getInventory().contains(Material.ENDER_PEARL))
					{
						ItemStack ep = new ItemStack(Material.ENDER_PEARL);
						ep.setAmount(1);
						
						p.getInventory().remove(ep);
						shootPearl(p);
					}
				}
				else
				{
					shootPearl(p);
				}
			}
			else
			{
				shootPearl(le);
			}
			
			e.setCancelled(true);
		}
	}
	
	private void shootPearl(LivingEntity le)
	{
		EnderPearl ep = le.launchProjectile(EnderPearl.class);
		ep.setShooter(le);
	}
}