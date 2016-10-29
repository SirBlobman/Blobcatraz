package com.SirBlobman.blobcatraz.utility.comparator;

import java.util.Comparator;

import org.bukkit.World;

public class WorldComparator implements Comparator<World>
{
	@Override
	public int compare(World w1, World w2)
	{
		String name1 = w1.getName();
		String name2 = w2.getName();
		return name1.compareTo(name2);
	}
}