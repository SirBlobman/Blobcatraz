package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.comparator.WorldComparator;

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
			String m2 = "  §6§lUsed RAM: §e" + (used / mb) + " MB";
			String m3 = "  §6§lAvailable RAM: §e" + (free / mb) + " MB";
			String m4 = "  §6§lMax RAM: §e" + (max / mb) + " MB";
			cs.sendMessage(new String[] {m1, m2, m3, m4});
			cs.sendMessage("  §6§lLoaded Chunks per World:\n");
			
			List<World> worlds = Bukkit.getWorlds();
			worlds.sort(new WorldComparator());
			for(World w : worlds)
			{
				Chunk[] chunks = w.getLoadedChunks();
				int size = chunks.length;
				String m5 = "    §e§l" + w.getName() + ": §a" + size + " chunks";
				cs.sendMessage(m5);
			}
			return true;
		}
		return false;
	}
}