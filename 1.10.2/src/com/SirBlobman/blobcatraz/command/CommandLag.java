package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandLag implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("lag"))
		{
			Runtime runtime = Runtime.getRuntime();
			int mb = 1024 * 1024;
			long total = runtime.totalMemory();
			long free = runtime.freeMemory();
			long used = total - free;
			long max = runtime.maxMemory();
			
			String m1 = Util.blobcatraz + "Runtime Info:";
			String m2 = "§6§lUsed RAM: §e" + (used / mb) + " MB";
			String m3 = "§6§lAvailable RAM: §e" + (free / mb) + " MB";
			String m4 = "§6§lMax RAM: §e" + (max / mb) + " MB";
			cs.sendMessage(new String[] {m1, m2, m3, m4});
			return true;
		}
		return false;
	}
}