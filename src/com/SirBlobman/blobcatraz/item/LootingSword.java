package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootingSword 
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
}
