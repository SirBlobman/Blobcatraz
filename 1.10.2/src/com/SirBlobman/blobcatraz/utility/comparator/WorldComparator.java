package com.SirBlobman.blobcatraz.utility.comparator;

import java.util.Comparator;

import org.bukkit.World;

public class WorldComparator implements Comparator<World>
{
	@Override
	public int compare(World w1, World w2)
	{
		String n1 = w1.getName();
		String n2 = w2.getName();
		return n1.compareToIgnoreCase(n2);
	}
}