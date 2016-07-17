package com.SirBlobman.blobcatraz.task;

import java.util.Map.Entry;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.listener.CombatLog;

public class CombatTag implements Runnable 
{
	@Override
	public void run() 
	{
		if(CombatLog.tagged.isEmpty()) return;
		for(Entry<Player, Long> key : CombatLog.tagged.entrySet())
		{
			long time = System.currentTimeMillis();
			long escape = key.getValue();
			if(time > escape)
			{
				CombatLog.tagged.remove(key.getKey());
				key.getKey().sendMessage(Util.blobcatraz + "You are no longer in combat!");
			}
		}
	}
}