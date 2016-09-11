package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class BowEnchantEvent extends EnchantEvent 
{
	private ItemStack bow;
	private ProjectileSource shooter;
	private EntityShootBowEvent bowEvent;
	
	public BowEnchantEvent(EntityShootBowEvent e, List<String> lore, ItemStack bow, ProjectileSource shooter)
	{
		super(lore);
		if(bow.getType() == Material.BOW) this.bow = bow;
		else throw new IllegalArgumentException("Argument 2 must be a BOW!");
		this.shooter = shooter;
	}
	
	public ItemStack getBow()
	{
		return bow;
	}
	
	public ProjectileSource getShooter()
	{
		return shooter;
	}
	
	public EntityShootBowEvent getBowEvent()
	{
		return bowEvent;
	}
}