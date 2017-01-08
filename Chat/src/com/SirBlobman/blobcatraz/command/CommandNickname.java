package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.listener.ListenPrefix;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandNickname implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("nick"))
		{
			if(args.length == 1)
			{
				if(cs instanceof Player)
				{
					Player p = (Player) cs;
					String nick = args[0];
					String cnick = Util.color(nick);
					YamlConfiguration config = ConfigDatabase.load(p);
					config.set("nick", nick);
					ConfigDatabase.save(p, config);
					ListenPrefix.setNick(p, cnick);;
					p.sendMessage(Util.prefix + Util.option("command.nickname.success", cnick));
					return true;
				}
				cs.sendMessage(Util.notPlayer);
				return true;
			}
			if(args.length > 1)
			{
				String target = args[0];
				Player p = Bukkit.getPlayer(target);
				if(p == null)
				{
					String msg = Util.prefix + Util.option("error.target.does not exist", target);
					cs.sendMessage(msg);
					return true;
				}
				
				String nick = args[1];
				String cnick = Util.color(nick);
				YamlConfiguration config = ConfigDatabase.load(p);
				config.set("nick", nick);
				ConfigDatabase.save(p, config);
				ListenPrefix.setNick(p, cnick);
				p.sendMessage(Util.prefix + Util.option("command.nickname.success", cnick));
				cs.sendMessage(Util.prefix + Util.option("command.nickname.other", p.getName(), cnick));
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
}