package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPDrain implements Listener 
{
	@EventHandler
	public void onPlayerHitWithXPStealEnchant(EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		Player damaged = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		if(damaged == null || damager == null) return;
		
		ItemStack held = damager.getItemInHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§6XP Drain I"))
		{
			if(damaged.getLevel() >= 5)
			{
				damaged.setLevel(damaged.getLevel() - 5);
				damager.setLevel(damager.getLevel() + 5);
			}
		}
	}
}