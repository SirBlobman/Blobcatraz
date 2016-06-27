package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items 
{
	public static ItemStack lootSword()
	{
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta meta = sword.getItemMeta();
		
		meta.setDisplayName("§eDrop Everything Sword");
		meta.setLore(Arrays.asList
		(
				"Whatever this sword kills will drop all its items",
				"Can only be used once"
		));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		sword.setItemMeta(meta);
		
		sword.setDurability((short) 59);
		sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1000);
		
		return sword;
	}
	
	public static ItemStack opSword()
	{
		ItemStack op_sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = op_sword.getItemMeta();
		
		meta.setDisplayName("§4§ki§9Overpowered §9Sword§4§ki§r");
		meta.setLore(Arrays.asList
		(
				"This sword will kill almost anything"
		));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.spigot().setUnbreakable(true);
		op_sword.setItemMeta(meta);
		
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.THORNS, 32767);
		
		return op_sword;
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
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.spigot().setUnbreakable(true);
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
		meta.setLore(Arrays.asList
				(
					"Left Click to set Position 1",
					"Right Click to set Position 2"
				));
		
		wand.setItemMeta(meta);
		wand.setAmount(1);
		
		return wand;
	}
}
