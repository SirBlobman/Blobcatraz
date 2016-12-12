package com.SirBlobman.blobcatraz.command;

import java.util.Comparator;

import org.bukkit.plugin.Plugin;

public class PluginComparator implements Comparator<Plugin>
{
	@Override
	public int compare(Plugin p1, Plugin p2)
	{
		String n1 = p1.getName();
		String n2 = p2.getName();
		return n1.compareTo(n2);
	}
}