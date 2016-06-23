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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class Ender implements Listener
{
	@EventHandler
	public void onShootArrow(EntityShootBowEvent e)
	{
		LivingEntity el = e.getEntity();
		if(el instanceof Player)
		{
			Player p = (Player) el;
			PlayerInventory pi = p.getInventory();
			ItemStack held = p.getItemInHand();
			ItemMeta hmeta = held.getItemMeta();
			List<String> hlore = hmeta.getLore();
			
			if(held.getType() == Material.BOW && hlore.contains("§7Ender I"))
			{
				if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
				{
					if(pi.contains(Material.ENDER_PEARL))
					{
						ItemStack ep = new ItemStack(Material.ENDER_PEARL);
						ep.setAmount(1);
						pi.remove(ep);
						shootPearl(p);
					}
				}
				if(p.getGameMode() == GameMode.CREATIVE)
				{
					shootPearl(p);
				}
				
				e.setCancelled(true);
			}
			else
			{
				return;
			}
		}
	}
	
	private void shootPearl(Player p)
	{
		EnderPearl ep = p.launchProjectile(EnderPearl.class);
				ep.setShooter(p);
	}
}