package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandHub;
import com.SirBlobman.blobcatraz.command.CommandSpawn;
import com.SirBlobman.blobcatraz.command.CommandTeleport;
import com.SirBlobman.blobcatraz.command.CommandWorld;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.utility.Util;

public class Teleportation extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		configs();
		commands();
		Util.broadcast("&2Teleportation Addon Enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&4Teleportation Addon Disabled");
	}
	
	private void configs()
	{
		ConfigSpawn.load();
	}
	
	private void commands()
	{
		c("center", new CommandTeleport(), null);
		c("teleport", new CommandTeleport(), null);
		c("spawn", new CommandSpawn(), null);
		c("setspawn", new CommandSpawn(), null);
		c("world", new CommandWorld(), null);
		c("worldlist", new CommandWorld(), null);
		c("hub", new CommandHub(), null);
		c("sethub", new CommandHub(), null);
	}
}