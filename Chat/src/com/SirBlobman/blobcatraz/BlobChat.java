package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandChat;
import com.SirBlobman.blobcatraz.command.CommandNickname;
import com.SirBlobman.blobcatraz.command.CommandPrefix;
import com.SirBlobman.blobcatraz.listener.ListenChat;
import com.SirBlobman.blobcatraz.listener.ListenFormat;
import com.SirBlobman.blobcatraz.listener.ListenPrefix;
import com.SirBlobman.blobcatraz.utility.Util;

public class BlobChat extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		Util.regEvents(new ListenChat(), new ListenPrefix(), new ListenFormat());
		Util.broadcast("&2Chat addon enabled");
		c("setprefix", new CommandPrefix(), null);
		c("nick", new CommandNickname(), null);
		c("chat", new CommandChat(), null);
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("&4Chat addon disabled");
	}
}