package com.SirBlobman.blobcatraz.gui;

import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.TeleportUtil.Teleport;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RandomTPItems
{
	public static ItemStack tp(Teleport type, Material mat)
	{
		if(type == null || mat == null) return null;
		ItemStack tp = new ItemStack(mat);
		ItemMeta meta = tp.getItemMeta();
		String name = "";
		switch(type)
		{
		case FAR:
			name = Util.format("&1Tiny&r");
			break;
		case NORMAL:
			name = Util.format("&2Normal&r");
			break;
		case TINY:
			name = Util.format("&3Far&r");
			break;
		}
		
		meta.setDisplayName(name);
		
		return tp;
	}
}