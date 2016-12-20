package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandKit;
import com.SirBlobman.blobcatraz.gui.GuiKits;
import com.SirBlobman.blobcatraz.utility.Util;

public class Kits extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		commands();
		Util.regEvents(new GuiKits());
		Util.broadcast("&2Kits are enabled");
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	private void commands()
	{
		c("kit", new CommandKit(), null);
		c("kits", new CommandKit(), null);
		c("createkit", new CommandKit(), null);
		c("deletekit", new CommandKit(), null);
		c("chesttokit", new CommandKit(), null);
		c("kittochest", new CommandKit(), null);
	}
}