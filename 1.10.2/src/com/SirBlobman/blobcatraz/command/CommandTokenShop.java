package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.gui.GuiTokenShop;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandTokenShop implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			
			if(command.equals("tokenshop"))
			{
				GuiTokenShop GTS = new GuiTokenShop();
				Inventory shop = GTS.main();
				PlayerUtil.open(p, shop);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}