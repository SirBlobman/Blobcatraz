package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class BowEnchantEvent extends EnchantEvent
{
	private ItemStack bow;
	private ProjectileSource shooter;
	
	public BowEnchantEvent(List<String> lore, ItemStack bow, ProjectileSource shooter)
	{
		super(lore);
		Material mat = bow.getType();
		if(mat == Material.BOW)
		{
			this.bow = bow;
			this.shooter = shooter;
		}
		else
		{
			IllegalArgumentException IAE = new IllegalArgumentException("Argument 2 MUST be a bow");
			throw IAE;
		}
	}
	
	public ItemStack getBow() {return bow;}
	public ProjectileSource getShooter() {return shooter;}
}