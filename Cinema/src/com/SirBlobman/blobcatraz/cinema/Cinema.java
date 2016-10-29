package com.SirBlobman.blobcatraz.cinema;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class Cinema extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		ConfigurationSerialization.registerClass(Movie.class);
		Util.print("Cinema: §1Enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.print("Cinema: §4Disabled");
	}
}