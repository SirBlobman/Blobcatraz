package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.entity.Player;

public class ArmorEnchantEvent extends EnchantEvent 
{
	private List<String> helmetLore, chestplateLore, leggingsLore, bootsLore;
	private Player player;
	
	public ArmorEnchantEvent(List<String> helmet, List<String> chestplate, List<String> leggings, List<String> boots, Player wearing)
	{
		super(helmet);
		helmetLore = helmet;
		chestplateLore = chestplate;
		leggingsLore = leggings;
		bootsLore = boots;
		player = wearing;
	}
	
	/**
	 * Deprecated and unusable!
	 * <br>Get the lore form each separate armor piece instead
	 * @return null
	 */
	@Deprecated
	public List<String> getLore() {return null;}
	public List<String> getHelmetLore() {return helmetLore;}
	public List<String> getChestplateLore() {return chestplateLore;}
	public List<String> getLeggingsLore() {return leggingsLore;}
	public List<String> getBootsLore() {return bootsLore;}
	public Player getPlayer() {return player;}
}