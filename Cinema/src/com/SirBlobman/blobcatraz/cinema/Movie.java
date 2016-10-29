package com.SirBlobman.blobcatraz.cinema;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

public class Movie implements ConfigurationSerializable
{
	private String name;
	private Location top, bottom;
	
	/**
	 * Used for creating moving pictures or GIFs as Minecraft Blocks
	 * @param name Name of the movie
	 * @param l1 Location of the top-left corner of the "screen"
	 * @param l2 Location of the bottom-right corner of the "screen"
	 * @throws IllegalArgumentException if name, l1, or l2 is {@code null}
	 */
	public Movie(String name, Location l1, Location l2)
	{
		if(name == null || l1 == null || l2 == null)
		{
			String error = "Name or Location cannot be NULL!";
			IllegalArgumentException IAE = new IllegalArgumentException(error);
			throw IAE;
		}
		this.name = name;
		this.top = l1;
		this.bottom = l2;
	}
	
	public Movie(Map<String, Object> map)
	{
		this
		(
			(String) map.get("name"),
			(Location) map.get("pos1"),
			(Location) map.get("pos2")
		);
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("name", this.name);
		map.put("pos1", this.top);
		map.put("pos2", this.bottom);
		return map;
	}
}