package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandWarp;
import com.SirBlobman.blobcatraz.gui.GuiWarps;
import com.SirBlobman.blobcatraz.utility.Util;

public class Warps extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		commands();
		events();
		Util.print("Warp extension is enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.print("Warp extension is disabled");
	}
	
	private void commands()
	{
		c("warp", new CommandWarp(), null);
		c("warps", new CommandWarp(), null);
		c("createwarp", new CommandWarp(), null);
		c("deletewarp", new CommandWarp(), null);
	}
	
	private void events()
	{
		Util.regEvents(new GuiWarps());
	}
}