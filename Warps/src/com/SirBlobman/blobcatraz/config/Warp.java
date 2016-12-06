package com.SirBlobman.blobcatraz.config;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Warp
{
	private final String name;
	private final ItemStack icon;
	private final Location location;
	
	Warp(String name, ItemStack icon, Location l)
	{
		this.name = name;
		this.icon = icon;
		this.location = l;
	}
	
	public String name() {return name;}
	public ItemStack icon() {return icon;}
	public Location location() {return location;}
}