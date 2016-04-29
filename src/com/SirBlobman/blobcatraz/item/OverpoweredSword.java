package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OverpoweredSword 
{
	public static ItemStack opsword()
	{
		ItemStack op_sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = op_sword.getItemMeta();
		
		meta.setDisplayName("§4§ki§9Overpowered Sword§4§ki§r");
		meta.setLore(Arrays.asList
		(
				"This sword will kill almost anything",
				"It is Awesome"
		));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		op_sword.setItemMeta(meta);
		
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
		op_sword.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
		
		return op_sword;
	}
}
