package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;

public class AFKCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args)
    {    
    	if(!(s instanceof Player))
    	{
    		s.sendMessage(Util.notAPlayer);
    		return true;
    	}
    	
    	Player p = (Player)s;
    	
        String name = p.getName();
        
        if(label.equalsIgnoreCase("afk"))
        {
            if(args.length < 1)
            {
                Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK");
                Database.setAFK(p);
            }
            else
            {
                String reason = Util.getFinalArg(args, 0);
                
                Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK:");
                Bukkit.broadcastMessage("     - " + reason);
                Database.setAFK(p);
            }
            return true;
        }
        return false;
    }
    
    public static void notAFK(Player p)
    {
        String name = p.getName();
        
        Bukkit.broadcastMessage("§6§l* §7" + name + " §6is no longer AFK");
        Database.setNotAFK(p);
    }
}