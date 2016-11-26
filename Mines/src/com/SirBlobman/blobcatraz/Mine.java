package com.SirBlobman.blobcatraz;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

public class Mine implements ConfigurationSerializable
{
	private String name;
	private Location l1;
	private Location l2;
	
	public Mine(String name, Location l1, Location l2)
	{
		this.name = name;
		this.l1 = l1;
		this.l2 = l2;
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = Maps.newTreeMap();
		map.put("Name", name);
		map.put("pos1", l1);
		map.put("pos2", l2);
		return map;
	}
	
	public static Mine deserialize(Map<String, Object> map)
	{
		String name = (String) map.get("Name");
		Location l1 = (Location) map.get("pos1");
		Location l2 = (Location) map.get("pos2");
		Mine M = new Mine(name, l1, l2);
		return M;
	}
}
