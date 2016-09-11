package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.entity.LivingEntity;

public class ArmorEnchantEvent extends EnchantEvent
{
	private List<String> helmet, chestplate, leggings, boots;
	private LivingEntity le;
	
	public ArmorEnchantEvent(List<String> helmet, List<String> chestplate, List<String> leggings, List<String> boots, LivingEntity le)
	{
		super(helmet);
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.le = le;
	}
	
	/**
	 * Unusable
	 * <br> Get the lore from each armor piece instead
	 * @return {@code null}
	 */
	@Deprecated
	public List<String> getLore() {return null;}
	public List<String> getHelmet() {return helmet;}
	public List<String> getChestplate() {return chestplate;}
	public List<String> getLeggings() {return leggings;}
	public List<String> getBoots() {return boots;}
	public LivingEntity getEntity() {return le;}
}