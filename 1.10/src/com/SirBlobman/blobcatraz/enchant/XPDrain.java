package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPDrain implements Listener
{
	@EventHandler
	public void attackWithXPStealEnchant(EntityDamageByEntityEvent e)
	{
		if(e.getEntity().getType() != EntityType.PLAYER || e.getDamager().getType() != EntityType.PLAYER) return;
		Player damaged = (Player) e.getDamager();
		Player damager = (Player) e.getEntity();
		ItemStack held = damager.getInventory().getItemInMainHand();
		if(held == null) held = damager.getInventory().getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§7XP Drain I") && damaged.getLevel() >= 5)
		{
			damaged.setLevel(damaged.getLevel() - 5);
			damager.setLevel(damager.getLevel() + 5);
		}
	}
}