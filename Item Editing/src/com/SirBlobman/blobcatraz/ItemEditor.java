package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandEdit;
import com.SirBlobman.blobcatraz.command.CommandItem;
import com.SirBlobman.blobcatraz.utility.Util;

public class ItemEditor extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		commands();
		Util.broadcast("&2ItemEditor Addon Enabled!");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&4ItemEditor Addon Disabled!");
	}
	
	private void commands()
	{
		c("item", new CommandItem(), new CommandItem());
		c("addlore", new CommandEdit(), null);
		c("clearlore", new CommandEdit(), null);
		c("removelore", new CommandEdit(), null);
		c("rename", new CommandEdit(), null);
		c("resetitem", new CommandEdit(), null);
		c("setlore", new CommandEdit(), null);
	}
}