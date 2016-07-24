package com.SirBlobman.blobcatraz.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.listener.CombatLog;
import com.SirBlobman.blobcatraz.scoreboard.CombatTimer;

public class CombatTag implements Runnable 
{
	CombatTimer CT = new CombatTimer(Blobcatraz.instance);
	
	@Override
	public void run() 
	{
		if(CombatLog.tagged.isEmpty()) return;
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!CombatLog.tagged.containsKey(p))
			{
				CT.clearTimer(p);
			}
			else
			{
				long time = System.currentTimeMillis();
				long escape = CombatLog.tagged.get(p);
				if(time > escape)
				{
					CombatLog.tagged.remove(p);
					p.sendMessage(Util.blobcatraz + "You are no longer in combat!");
					CT.clearTimer(p);
				}
				else
				{
					int i = (int) (time - escape);
					int seconds = i / -1000;
					CT.setTimer(p, seconds);
				}
			}
		}
	}
}