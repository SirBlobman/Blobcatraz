package com.SirBlobman.blobcatraz.command;

import java.io.File;

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
        String name = s.getName();
        
        if(label.equalsIgnoreCase("afk"))
        {
            if(args.length < 1)
            {
                Util.broadcast("§6§l* §7" + name + "§6is now AFK");
                afkConfig.set(name + ".afk", true);
            }
            else
            {
                String reason = Util.getFinalArg(args, 0);
                
                Util.broadcast("§6§l* §7" + name + " §6is now AFK:");
                Util.broadcast("     - " + reason);
                afkConfig.set(name + ".afk", true);
            }
            return true;
        }
        return false;
    }
    
    public static void notAFK(Player p)
    {
        String name = p.getName();
        
        Util.broadcast("§6§l* §7" + name + "§6is no longer AFK");
        AFKCommand.afkConfig.set(name + ".afk", false);
    }
}