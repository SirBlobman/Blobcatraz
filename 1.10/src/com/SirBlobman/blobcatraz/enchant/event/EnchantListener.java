package com.SirBlobman.blobcatraz.enchant.event;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.enchant.Ender;

public class EnchantListener implements Listener
{
	Server S = Bukkit.getServer();
	PluginManager PM = S.getPluginManager();
	BukkitScheduler BS = S.getScheduler();
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		if(!(e.getEntity() instanceof LivingEntity) || !(e.getDamager() instanceof LivingEntity)) return;
		LivingEntity damaged = (LivingEntity) e.getEntity();
		LivingEntity damager = (LivingEntity) e.getDamager();
		EntityEquipment ee = damager.getEquipment();
		ItemStack held = ee.getItemInMainHand();
		if(held == null) held = ee.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		PM.callEvent(new DamageEnchantEvent(lore, damaged, damager, e.getDamage()));
	}
	
	@EventHandler
	public void rightClick(PlayerInteractEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			ItemStack held = e.getItem();
			if(held == null) return;
			ItemMeta meta = held.getItemMeta();
			if(meta == null) return;
			List<String> lore = meta.getLore();
			if(lore == null) return;
			Block b = e.getClickedBlock();
			PM.callEvent(new RightClickEnchantEvent(lore, p, b));
		}
	}
	
	@EventHandler
	public void bowShoot(EntityShootBowEvent e)
	{
		if(e.isCancelled()) return;
		Entity proj = e.getProjectile();
		if(!(proj instanceof Projectile)) return;
		Projectile pj = (Projectile) proj;
		ProjectileSource shooter = pj.getShooter();
		ItemStack bow = e.getBow();
		if(bow == null) return;
		ItemMeta meta = bow.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		if(lore.contains(Ender.ender + "I")) e.setCancelled(true);
		PM.callEvent(new BowEnchantEvent(e, lore, bow, shooter));
	}
	
	@EventHandler
	public void interact(PlayerInteractEntityEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		PM.callEvent(new InteractEntityEnchantEvent(lore, ent, p));
	}
	
	public void armor()
	{
		BS.runTaskTimer(Blobcatraz.instance, new Armor(), 0L, 9 * 20L);
	}
	
	private class Armor implements Runnable
	{
		@Override
		public void run()
		{
			for(Player p : S.getOnlinePlayers())
			{
				List<List<String>> lores = getArmorLores(p.getInventory());
				if(lores == null) return;
				if(lores.size() != 4) return;
				PM.callEvent(new ArmorEnchantEvent(lores.get(0), lores.get(1), lores.get(2), lores.get(3), p));
			}
		}
		
		private List<List<String>> getArmorLores(PlayerInventory pi)
		{
			ItemStack head = pi.getHelmet();
			ItemStack chest = pi.getChestplate();
			ItemStack legs = pi.getLeggings();
			ItemStack feet = pi.getBoots();
			if(head == null || chest == null || legs == null || feet == null) return null;
			ItemMeta metah = head.getItemMeta();
			ItemMeta metac = chest.getItemMeta();
			ItemMeta metal = legs.getItemMeta();
			ItemMeta metaf = feet.getItemMeta();
			if(metah == null || metac == null || metal == null || metaf == null) return null;
			List<String> loreh = metah.getLore();
			List<String> lorec = metac.getLore();
			List<String> lorel = metal.getLore();
			List<String> loref = metaf.getLore();
			if(loreh == null || lorec == null || lorel == null || loref == null) return null;
			return Arrays.asList(loreh, lorec, lorel, loref);
		}
	}
}