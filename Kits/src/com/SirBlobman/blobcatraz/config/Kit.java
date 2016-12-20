package com.SirBlobman.blobcatraz.config;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Kit
{
	private List<ItemStack> items;
	private String name;
	private ItemStack icon;
	
	Kit(String name, ItemStack icon, List<ItemStack> items)
	{
		this.name = name;
		this.icon = icon;
		this.items = items;
	}
	
	public List<ItemStack> items() {return items;}
	public ItemStack icon() {return icon;}
	public String name() {return name;}
}