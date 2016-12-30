package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.listener.ListenCobbleOres;
import com.SirBlobman.blobcatraz.utility.Util;

public class SkyBlock extends Blobcatraz
{
	@Override
	public void onEnable()
	{
		Util.regEvents(new ListenCobbleOres());
		Util.broadcast("SkyBlock Addon &2Enabled");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("SkyBlock Addon &4Disabled");
	}
}