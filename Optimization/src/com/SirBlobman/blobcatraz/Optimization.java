package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandLagg;
import com.SirBlobman.blobcatraz.utility.Util;

public class Optimization extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		c("lagg", new CommandLagg(), null);
		Util.broadcast("&2AntiLag Enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&4AntiLag Disabled");
	}
}