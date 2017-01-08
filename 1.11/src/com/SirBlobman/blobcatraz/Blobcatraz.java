package com.SirBlobman.blobcatraz;

import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.utility.Util;

/**
 * Main class for Blobcatraz<br/>
 * To create an addon, you must add "{@code depend: [Blobcatraz]}" to your {@code plugin.yml}<br/>
 * You can also extend this class, but that is not required
 * @author SirBlobman
 * @see JavaPlugin
 */
public class Blobcatraz extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();
		Util.broadcast("&2Enabling...");
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		Util.broadcast("&4Disabling...");
	}
}