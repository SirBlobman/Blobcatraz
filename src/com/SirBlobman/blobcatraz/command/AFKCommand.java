package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class AFKCommand implements CommandExecutor
{
    public static File afkFile = new File(Blobcatraz.instance.getDataFolder(), "afk.yml");
    public static FileConfiguration afkConfig = YamlConfiguration.loadConfiguration(afkFile);
    
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args)
    {    
    	if(!(s instanceof Player))
    	{
    		s.sendMessage(Util.notAPlayer);
    		return true;
    	}
    	
    	Player p = (Player)s;
    	
        UUID uuid = p.getUniqueId();
        String name = p.getName();
        
        if(label.equalsIgnoreCase("afk"))
        {
            if(args.length < 1)
            {
                Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK");
                afkConfig.set(uuid.toString() + ".afk", true);
            }
            else
            {
                String reason = Util.getFinalArg(args, 0);
                
                Bukkit.broadcastMessage("§6§l* §7" + name + " §6is now AFK:");
                Bukkit.broadcastMessage("     - " + reason);
                afkConfig.set(uuid.toString() + ".afk", true);
            }
            return true;
        }
        return false;
    }
    
    public static void notAFK(Player p)
    {
        String uuid = p.getUniqueId().toString();
        String name = p.getName();
        
        Bukkit.broadcastMessage("§6§l* §7" + name + " §6is no longer AFK");
        AFKCommand.afkConfig.set(uuid + ".afk", false);
    }
}