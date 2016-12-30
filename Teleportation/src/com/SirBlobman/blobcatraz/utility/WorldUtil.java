package com.SirBlobman.blobcatraz.utility;

import java.util.Comparator;
import java.util.List;

import org.bukkit.World;

import com.SirBlobman.blobcatraz.utility.compare.WorldComparator;

public class WorldUtil extends Util 
{
	public static List<World> abc()
	{
		List<World> worlds = S.getWorlds();
		Comparator<World> c = new WorldComparator();
		worlds.sort(c);
		return worlds;
	}
	
	public static List<World> zyx()
	{
		List<World> worlds = S.getWorlds();
		Comparator<World> c = new WorldComparator();
		worlds.sort(c.reversed());
		return worlds;
	}
	
	public static String joinWorlds()
	{
		StringBuilder sb = new StringBuilder();
		for(World w : abc())
		{
			String world = w.getName();
			if(sb.length() != 0) sb.append("§r, ");
			sb.append("§2" + world);
		}
		return sb.toString();
	}
}