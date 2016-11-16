package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandRules implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("rules"))
		{
			if(!PlayerUtil.hasPermission(cs, "blobcatraz.rules")) return true;
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				if(sub.equals("staff"))
				{
					if(!PlayerUtil.hasPermission(cs, "blobcatraz.rules.staff")) return true;

					String[] rules = rules("staff_rules.txt");
					for(String rule : rules)
					{
						String msg = Util.format(rule);
						cs.sendMessage(msg);
					}
				}
				return true;
			}
			
			String[] rules = rules("rules.txt");
			for(String rule : rules)
			{
				String msg = Util.format(rule);
				cs.sendMessage(msg);
			}
			return true;
		}
		return false;
	}
	
	private String[] rules(String fileName)
	{
		File rules = new File(Blobcatraz.folder, fileName);
		FileConfiguration config = YamlConfiguration.loadConfiguration(rules);
		if(!rules.exists()) 
		{
			try
			{
				rules.createNewFile();
				config.set("rules", Arrays.asList("1)", "2)", "3)"));
				config.save(rules);
			} catch(Exception ex) {Util.print("Failed to create " + rules);}
		}
		
		
		try{config.load(rules);}
		catch(Exception ex) {Util.print("Error reading " + rules + ": " + ex.getMessage()); return null;}
		List<String> ruleList = config.getStringList("rules");
		if(ruleList == null || ruleList.isEmpty()) {rules.delete(); return rules(fileName);}
		return ruleList.toArray(new String[0]);
	}
}