package com.SirBlobman.blobcatraz.config;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Warp
{
	String name;
	ItemStack icon;
	Location location;
	
	Warp(String name, ItemStack icon, Location location)
	{
		this.name = name;
		this.icon = icon;
		this.location = location;
	}
	
	public String getName() {return name;}
	public ItemStack getItem() {return icon;}
	public Location getLocation() {return location;}
}