package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.utility.Util;

public class Mines extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		Util.print("[Prison Mines] enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.print("[Prison Mines] disabled");
	}
}