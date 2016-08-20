package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.gui.GuiTokenShop;

public class CommandTokenShop implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("tshop") || label.equalsIgnoreCase("tokenshop"))
		{
			GuiTokenShop GTS = new GuiTokenShop();
			GTS.open(p, GTS.mainGUI());
			return true;
		}
		return false;
	}
}