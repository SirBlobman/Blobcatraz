package com.SirBlobman.blobcatraz.command;

import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CommandHome implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(command.equals("home")) return home(p, args);
			if(command.equals("sethome")) return sethome(p, args);
            if(command.equals("delhome")) return delhome(p, args);
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean home(Player p, String[] args)
	{
		String permission = "blobcatraz.home";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length == 0)
		{
			List<String> list = ConfigDatabase.getHomes(p);
			StringBuffer homes = new StringBuffer();
			for(int i = 0; i < list.size(); i++)
			{
				if(i != 0) homes.append("§r, ");
				String home = "§6" + list.get(i);
				homes.append(home);
			}
			
			p.sendMessage(Util.blobcatraz + "Homes: ");
			p.sendMessage(homes.toString());
			return true;
		}
		
		String home = Util.getFinal(args, 0);
		ConfigDatabase.teleportHome(home, p);
		p.sendMessage(Util.blobcatraz + "You are now home!");
		return true;
	}
	
	private boolean sethome(Player p, String[] args)
	{
		String permission = "blobcatraz.sethome";
		String multi = permission + ".multiple";
		
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		List<String> homes = ConfigDatabase.getHomes(p);
		int size = homes.size();
		if(size >= 1 && !PlayerUtil.hasPermission(p, multi)) return true;
		
		if(args.length >= 1)
		{
			String home = Util.getFinal(args, 0);
			ConfigDatabase.setHome(p, p.getLocation(), home);
			p.sendMessage(Util.blobcatraz + "Your home has been set!");
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
    
    private boolean delhome(Player p, String[] args)
    {
        String permission = "blobcatraz.delhome";
        if(!PlayerUtil.hasPermission(p, permission)) return true;
        
        String home = Util.getFinal(args, 0);
        YamlConfiguration config = ConfigDatabase.load(p);
        config.set("homes. " + home, null);
        ConfigDatabase.save(p, config);
        p.sendMessage(Util.blobcatraz + "You deleted a home named §e" + home);
        return true;
    }
}