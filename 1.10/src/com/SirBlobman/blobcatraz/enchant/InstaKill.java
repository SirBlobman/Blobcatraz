package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InstaKill implements Listener
{
	@EventHandler
	public void onHitWithInstaKillEnchant(EntityDamageByEntityEvent e)
	{
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if(!(damager instanceof LivingEntity) || !(damaged instanceof LivingEntity)) return;
		LivingEntity ded = (LivingEntity) damaged;
		LivingEntity der = (LivingEntity) damager;
		
		EntityEquipment ee = der.getEquipment();
		ItemStack held = ee.getItemInMainHand();
		if(held == null) held = ee.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§7InstaKill I"))
		{
			try{ded.damage(ded.getMaxHealth());} catch (Exception ex) {}
			if(!ded.isDead()) ded.remove();
		}
	}
	
	@EventHandler
	public void onRightClickWithInstaKill(PlayerInteractEntityEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		Entity ent = e.getRightClicked();
		if(!(ent instanceof LivingEntity)) return;
		LivingEntity ded = (LivingEntity) ent;
		
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§7InstaKill I"))
		{
			try{ded.damage(ded.getMaxHealth());} catch (Exception ex) {}
			if(!ded.isDead()) ded.remove();
		}
	}
}