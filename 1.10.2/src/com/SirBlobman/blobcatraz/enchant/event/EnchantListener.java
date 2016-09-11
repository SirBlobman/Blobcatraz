package com.SirBlobman.blobcatraz.enchant.event;

import java.util.Arrays;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.enchant.Enchant;
import com.SirBlobman.blobcatraz.enchant.event.ClickEnchantEvent.ClickType;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;

public class EnchantListener implements Listener
{
	Server S = Bukkit.getServer();
	PluginManager PM = S.getPluginManager();
	BukkitScheduler BS = S.getScheduler();
	
	public void start()
	{
		Util.regEvents(this);
		BS.runTaskTimer(Blobcatraz.instance, new Armor(), 0L, 9 * 20L);
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if(damaged instanceof LivingEntity && damager instanceof LivingEntity)
		{
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
			DamageEnchantEvent DEE = new DamageEnchantEvent(lore, ded, der, e.getDamage());
			PM.callEvent(DEE);
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action a = e.getAction();
		Action left = Action.LEFT_CLICK_BLOCK;
		Action left2 = Action.LEFT_CLICK_AIR;
		Action right = Action.RIGHT_CLICK_BLOCK;
		Action right2 = Action.RIGHT_CLICK_AIR;
		ItemStack held = ItemUtil.getHeld(p);
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		Block b = e.getClickedBlock();
		if(b == null) return;
		if(a.equals(left) || a.equals(left2))
		{
			ClickType LEFT = ClickType.LEFT;
			ClickEnchantEvent CEE = new ClickEnchantEvent(lore, p, b, LEFT);
			PM.callEvent(CEE);
		}
		if(a.equals(right) || a.equals(right2))
		{
			ClickType RIGHT = ClickType.RIGHT;
			ClickEnchantEvent CEE = new ClickEnchantEvent(lore, p, b, RIGHT);
			PM.callEvent(CEE);
		}
	}
	
	@EventHandler
	public void shoot(EntityShootBowEvent e)
	{
		if(e.isCancelled()) return;
		Entity ent = e.getProjectile();
		if(ent instanceof Projectile)
		{
			Projectile pj = (Projectile) ent;
			ProjectileSource pjs = pj.getShooter();
			ItemStack bow = e.getBow();
			if(bow == null) return;
			ItemMeta meta = bow.getItemMeta();
			if(meta == null) return;
			List<String> lore = meta.getLore();
			if(lore == null) return;
			String ENDER = Enchant.ENDER.getName();
			for(String s : lore) 
			{
				if(s.contains(ENDER)) e.setCancelled(true);
			}
			
			BowEnchantEvent BEE = new BowEnchantEvent(lore, bow, pjs);
			PM.callEvent(BEE);
		}
	}
	
	@EventHandler
	public void interact(PlayerInteractEntityEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		ItemStack held = ItemUtil.getHeld(p);
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		InteractEnchantEvent IEE = new InteractEnchantEvent(lore, ent, p);
		PM.callEvent(IEE);
	}
	
	private class Armor implements Runnable
	{
		@EventHandler
		public void run()
		{
			for(World w : S.getWorlds())
			{
				for(LivingEntity le : w.getLivingEntities())
				{
					if(le.isValid())
					{
						EntityEquipment ee = le.getEquipment();
						List<List<String>> lores = getArmorLores(ee);
						if(lores != null && lores.size() == 4)
						{
							List<String> helmet = lores.get(0);
							List<String> chestplate = lores.get(1);
							List<String> leggings = lores.get(2);
							List<String> boots = lores.get(3);
							ArmorEnchantEvent AEE = new ArmorEnchantEvent(helmet, chestplate, leggings, boots, le);
							PM.callEvent(AEE);
						}						
					}					
				}
			}
		}
		
		private List<List<String>> getArmorLores(EntityEquipment ee)
		{
			ItemStack head = ee.getHelmet();
			ItemStack chest = ee.getChestplate();
			ItemStack legs = ee.getLeggings();
			ItemStack feet = ee.getBoots();
			if(head == null || chest == null || legs == null || feet == null) return null;
			List<String> loreh = ItemUtil.getLore(head);
			List<String> lorec = ItemUtil.getLore(chest);
			List<String> lorel = ItemUtil.getLore(legs);
			List<String> loreb = ItemUtil.getLore(feet);
			if(loreh == null || lorec == null || lorel == null || loreb == null) return null;
			return Arrays.asList(loreh, lorec, lorel, loreb);
		}
	}
}