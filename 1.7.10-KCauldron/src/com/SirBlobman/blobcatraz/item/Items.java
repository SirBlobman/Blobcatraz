package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items 
{
	public static ItemStack lootSword()
	{
		ItemStack lootSword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta meta = lootSword.getItemMeta();
		
		meta.setDisplayName("§eDrop Everything Sword");
		meta.setLore(Arrays.asList("Whatever this sword kills will drop all its items"));
		lootSword.setItemMeta(meta);
		
		lootSword.setDurability((short)59);
		lootSword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1000);
		
		return lootSword;
	}
	
	public static ItemStack opSword()
	{
		ItemStack opSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = opSword.getItemMeta();
		
		meta.setDisplayName("§4§ki§9Overpowered §9Sword§4§ki§r");
		meta.setLore(Arrays.asList("This sword will kill almost anything"));
		opSword.setItemMeta(meta);
		
		opSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		opSword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
		opSword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
		opSword.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
		opSword.addUnsafeEnchantment(Enchantment.THORNS, 32767);
		
		return opSword;
	}
	
	public static ItemStack opBow()
	{
		ItemStack opBow = new ItemStack(Material.BOW);
		ItemMeta meta = opBow.getItemMeta();
		
		meta.setDisplayName("§4§ki§9Overpowered §9Bow§4§ki§r");
		meta.setLore(Arrays.asList
		(
			"This bow will kill almost anything"
		));
		opBow.setItemMeta(meta);
		
		opBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 32767);
		opBow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 32767);
		opBow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 32767);
		opBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 32767);
		opBow.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		opBow.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
		opBow.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
		opBow.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
		opBow.addUnsafeEnchantment(Enchantment.THORNS, 32767);
		
		return opBow;
	}
	
	public static ItemStack portalWand()
	{
		ItemStack wand = new ItemStack(Material.STICK);
		ItemMeta meta = wand.getItemMeta();
		
		meta.setDisplayName("§3Blobcatraz §cPortal §cWand");
		meta.setLore(Arrays.asList("Left Click to set Position 1", "Right Click to set Position 2"));
		
		wand.setItemMeta(meta);
		wand.setAmount(1);
		
		return wand;
	}
	
	public static ItemStack voteDiamonds(String voter, String service)
	{
		ItemStack diamonds = new ItemStack(Material.DIAMOND);
		ItemMeta meta = diamonds.getItemMeta();
		
		meta.setDisplayName("§1§ki§4Vote§1§ki§r §3Diamonds");
		meta.setLore(Arrays.asList("§1Thanks", "§fSite: §6" + service, "§fVoter: §1" + voter));
		
		diamonds.setItemMeta(meta);
		diamonds.setAmount(10);
		
		return diamonds;
	}
	
	public static ItemStack voteWood(String voter, String service)
	{
		Random r = new Random();
		short damage = (short) r.nextInt(5);
		
		ItemStack wood = new ItemStack(Material.WOOD);
		ItemMeta meta = wood.getItemMeta();
		
		meta.setDisplayName("§1§ki§4Vote§1§ki§r §cWood");
		meta.setLore(Arrays.asList("§1Thanks!", "§fSite: §6" + service, "§fVoter: §1" + voter));
		
		wood.setItemMeta(meta);
		wood.setDurability(damage);
		wood.setAmount(64);
		
		return wood;
	}
}