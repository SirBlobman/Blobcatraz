package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandPortal;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.listener.ListenPortal;
import com.SirBlobman.blobcatraz.listener.ListenPortalWand;
import com.SirBlobman.blobcatraz.utility.Util;

public class Portals extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		ConfigPortals.load();
		commands();
		Util.regEvents(new ListenPortal(), new ListenPortalWand());
		Util.broadcast("&2Portals Addon Enabled!");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&4Portals Addon Disabled!");
	}
	
	private void commands()
	{
		c("portal", new CommandPortal(), null);
	}
}