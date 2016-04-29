package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PortalWand 
{
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
		
		return wand;
	}
}
