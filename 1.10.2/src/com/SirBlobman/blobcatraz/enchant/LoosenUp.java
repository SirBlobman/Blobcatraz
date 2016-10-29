package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class LoosenUp implements Listener
{
	public static final String LU = Enchant.LOOSE.getName();
	private final String lu1 = LU + " §fI";
	private final String lu2 = lu1 + "I";
	private final String lu3 = lu2 + "I";
	
	@EventHandler
	public void loose(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity ded = e.getDamaged();
		
		double chance = Math.random();
		if(lore.contains(lu1) && chance <= 0.10) armor(ded);
		if(lore.contains(lu2) && chance <= 0.15) armor(ded);
		if(lore.contains(lu3) && chance <= 0.25) armor(ded);
	}
	
	private void armor(LivingEntity le)
	{
		EntityEquipment ee = le.getEquipment();
		ItemStack[] armor = ee.getArmorContents().clone();
		ItemStack AIR = new ItemStack(Material.AIR);
		ItemStack[] air = new ItemStack[] {AIR, AIR, AIR, AIR};
		ee.setArmorContents(air);
		for(ItemStack is : armor)
		{
			Location l = le.getLocation();
			World w = l.getWorld();
			w.dropItem(l, is);
		}
	}
}