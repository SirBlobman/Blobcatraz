package com.SirBlobman.blobcatraz;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.listeners.AFK;
import com.SirBlobman.blobcatraz.listeners.CellBarsUnbreakableWithoutPerm;
import com.SirBlobman.blobcatraz.listeners.ChatMute;
import com.SirBlobman.blobcatraz.listeners.GiantDropsNotchApple;
import com.SirBlobman.blobcatraz.listeners.JoinBroadcast;
import com.SirBlobman.blobcatraz.listeners.LeaveBroadcast;
import com.SirBlobman.blobcatraz.listeners.LightningRod;
import com.SirBlobman.blobcatraz.listeners.SonicScrewdriver;
import com.SirBlobman.blobcatraz.listeners.UnkillableSlimes;
import com.SirBlobman.blobcatraz.parts.Recipes;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings({ "unchecked", "rawtypes", "unused", "deprecation" })
public final class Blobcatraz extends JavaPlugin implements Listener 
{
	public static Blobcatraz instance;
	public FileConfiguration config = getConfig();
	public static Blobcatraz plugin;

	HashMap<UUID, Location> pos1 = new HashMap();
	HashMap<UUID, Location> pos2 = new HashMap();
	List<String> portals = new ArrayList();
	List<UUID> inPortal = new ArrayList();

	public void savePortals() 
	{
		savePortalConfig();
		this.portals.clear();
		for (String x : getPortalConfig().getKeys(false)) 
		{
			this.portals.add(x);
		}
	}

	@Override
	public void onEnable() 
	{
		plugin = this;
		Recipes.onEnable();
		instance = this;
		config.addDefault("chat.disabled", false);
		config.addDefault("protection.prevent-prison-escape", true);
		config.addDefault("random.unkillable-slimes", false);
		config.addDefault("random.giant-drops-notch-apple", false);
		config.addDefault("sonic-screwdriver.enabled", true);
		config.options().copyDefaults(true);
		saveConfig();
		savePortalConfig();
		getServer().getPluginManager().registerEvents(this, this);
		
		getPortalConfig().options().copyDefaults(true);
		savePortals();
		
	// Normal Listeners
		getServer().getPluginManager().registerEvents(new JoinBroadcast(), this);
		getServer().getPluginManager().registerEvents(new LeaveBroadcast(), this);
		getServer().getPluginManager().registerEvents(new AFK(), this);
		getServer().getPluginManager().registerEvents(new LightningRod(), this);
		getServer().getPluginManager().registerEvents(new ChatMute(), this);
	// Config Defined Listeners
		if (config.getBoolean("random.unkillable-slimes") == true) 
		{
			getServer().getPluginManager().registerEvents(new UnkillableSlimes(), this);
		}

		if (config.getBoolean("random.giant-drops-notch-apple") == true) 
		{
			getServer().getPluginManager().registerEvents(new GiantDropsNotchApple(), this);
		}

		if (config.getBoolean("protection.prevent-prison-escape") == true) 
		{
			getServer().getPluginManager().registerEvents(new CellBarsUnbreakableWithoutPerm(), this);
		}
		if(config.getBoolean("sonic-screwdriver.enabled") == true)
		{
			getServer().getPluginManager().registerEvents(new SonicScrewdriver(), this);
		}
		Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r This plugin has been §2§lenabled§r!");
	}

	@Override
	public void onDisable() 
	{
		Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r This plugin has been §4§ldisabled§r!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		String ssender = sender.getName();
		
		if(c.getName().equalsIgnoreCase("chat"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("status"))
				{
					boolean status = config.getBoolean("chat.disabled");
					if(status == true)
					{
						sender.sendMessage("§1[§6Blobcatraz§1]§r §eThe chat is: §4Off");
					}
					if(status == false)
					{
						sender.sendMessage("§1[§6Blobcatraz§1]§r §eThe chat is: §2On");
					}
				}
				if(args[0].equalsIgnoreCase("on"))
				{
					this.config.set("chat.disabled", false);
					this.config.set("chat.disabled", false);
					this.config.set("chat.disabled", false);
					this.config.set("chat.disabled", false);
					this.config.set("chat.disabled", false);
					this.config.set("chat.disabled", false);
					ChatMute.isGlobalMute = false;
					this.saveConfig();
					this.saveConfig();
					this.saveConfig();
					this.reloadConfig();
					Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §5" + ssender + " §ehas §9un-muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("off"))
				{
					this.config.set("chat.disabled", true);
					this.config.set("chat.disabled", true);
					this.config.set("chat.disabled", true);
					this.config.set("chat.disabled", true);
					this.config.set("chat.disabled", true);
					this.config.set("chat.disabled", true);
					ChatMute.isGlobalMute = true;
					this.saveConfig();
					this.saveConfig();
					this.saveConfig();
					this.reloadConfig();
					Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §5" + ssender + " §ehas §9muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("clear"))
				{
					int line = 1;
					while(line <= 500)
					{
						Bukkit.broadcastMessage("§l");
						line++;
					}
					Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §5" + ssender + " §ehas §9cleared§e the chat!");
					return true;
				}
			}
		}
		
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage("§1[§6Blobcatraz§1]§r Commands cannot be executed via the console.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(c.getName().equalsIgnoreCase("random"))
		{
			int random_number;
			
			Random r = new Random();
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("help"))
				{
					sender.sendMessage(c.getUsage());
					return true;
				}
			}
			
			if(args.length == 2)
			{
				int min = Integer.parseInt(args[0]);
				int max = Integer.parseInt(args[1]);
				random_number = r.nextInt(max - min + 1) + min;
			}
			else
			{
				random_number = r.nextInt(32767);
			}
			sender.sendMessage("§1[§6Blobcatraz§1]§r Your random number is: " + random_number);
			return true;
		}
		
		if(c.getName().equalsIgnoreCase("rename"))
		{
			if(args.length > 0)
			{
				ItemMeta meta = p.getItemInHand().getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getFinalArg(args, 0)));
				p.getItemInHand().setItemMeta(meta);
				sender.sendMessage("§1[§6Blobcatraz§1]§r Currently held item was renamed to " + getFinalArg(args, 0) + "!");
				return true;
			}
		}
		if (c.getName().equalsIgnoreCase("blobcatraz")) 
		{
			if (args.length > 0) 
			{
				if (args[0].equals("reload") == true) 
				{
					reloadConfig();
					reloadPortalConfig();
					sender.sendMessage("§1[§6Blobcatraz§1]§r Configs reloaded");
				}
			} 
			else 
			{
				sender.sendMessage("§1[§6Blobcatraz§1]§r Thanks for using this plugin with /blobcatraz");
				sender.sendMessage(
						"§1[§6Blobcatraz§1]§r This command is currently useless unless you like seeing these messages!");
			}
			return true;
		}
		if (c.getName().equalsIgnoreCase("not-afk")) 
		{
			Bukkit.broadcastMessage("§6§l* §7" + sender.getName() + " §6is no longer AFK");
			return true;
		}
		if (c.getName().equalsIgnoreCase("afk")) 
		{
			if (args.length < 1) 
			{
				Bukkit.broadcastMessage("§6§l* §7" + sender.getName() + " §6is now AFK");
				AFK.AFK = true;
			} 
			else 
			{
				String reason = getFinalArg(args, 0);
				Bukkit.broadcastMessage("§6§l* §7" + sender.getName() + " §6is now AFK: ");
				Bukkit.broadcastMessage("    - " + reason);
				AFK.AFK = true;
			}
			return true;
		}
		if (c.getName().equalsIgnoreCase("findorigin")) 
		{
			Plugin pl = Blobcatraz.instance;
			for (Hologram h : HologramsAPI.getHolograms(pl)) 
			{
				deleteOld(h);
			}
			Location w = new Location(p.getWorld(), 0.0, 64.0, 0.0);
			Hologram h = HologramsAPI.createHologram(pl, w);
			TextLine t1 = h.appendTextLine("§1[§6Blobcatraz§1]§r This is the Origin");
			TextLine t2 = h.insertTextLine(1, "          It is located at x = 0, y = 64, and z = 0");
			ItemLine i1 = h.appendItemLine(new ItemStack(Material.BEDROCK));
			p.teleport(new Location(p.getWorld(), 0.0, 64.0, 0.0));
			return true;
		}
		try 
		{
			if (label.equalsIgnoreCase("portal")) 
			{
				if (args[0].equalsIgnoreCase("wand")) 
				{
					ItemStack gold_hoe = new ItemStack(Material.GOLD_HOE);
					ItemMeta gold_hoe_meta = gold_hoe.getItemMeta();
					gold_hoe_meta.setDisplayName("§3Blobcatraz §cPortal §cWand");
					gold_hoe_meta
							.setLore(Arrays.asList("Left Click to set Position 1", "Right Click to set Position 2"));
					gold_hoe.setItemMeta(gold_hoe_meta);
					p.getInventory().addItem(gold_hoe);
					sender.sendMessage("§1[§6Blobcatraz§1]§r Given you the portal wand!");
					return true;
				} 
				else if (args[0].equalsIgnoreCase("create")) 
				{
					UUID id = p.getUniqueId();
					if (args.length > 2) 
					{
						List<String> new_args = new ArrayList(Arrays.asList(args));
						new_args.remove(0);
						new_args.remove(0);
						StringBuilder command = new StringBuilder();
						for (String x : new_args) 
						{
							command.append(x + " ");
						}
						getPortalConfig().set(args[1] + ".pos1.world",
								((Location) this.pos1.get(id)).getWorld().getName());
						getPortalConfig().set(args[1] + ".pos1.x",
								Double.valueOf(((Location) this.pos1.get(id)).getX()));
						getPortalConfig().set(args[1] + ".pos1.y",
								Double.valueOf(((Location) this.pos1.get(id)).getY()));
						getPortalConfig().set(args[1] + ".pos1.z",
								Double.valueOf(((Location) this.pos1.get(id)).getZ()));
						getPortalConfig().set(args[1] + ".pos2.world",
								((Location) this.pos2.get(id)).getWorld().getName());
						getPortalConfig().set(args[1] + ".pos2.x",
								Double.valueOf(((Location) this.pos2.get(id)).getX()));
						getPortalConfig().set(args[1] + ".pos2.y",
								Double.valueOf(((Location) this.pos2.get(id)).getY()));
						getPortalConfig().set(args[1] + ".pos2.z",
								Double.valueOf(((Location) this.pos2.get(id)).getZ()));
						getPortalConfig().set(args[1] + ".command", "/" + command.toString());
						savePortals();
						sender.sendMessage(
								"§1[§6Blobcatraz§1]§r Created a command portal called §2§l" + args[1] + "§a!");
						return true;
					}
					getPortalConfig().set(args[1] + ".pos1.world", ((Location) this.pos1.get(id)).getWorld().getName());
					getPortalConfig().set(args[1] + ".pos1.x", Double.valueOf(((Location) this.pos1.get(id)).getX()));
					getPortalConfig().set(args[1] + ".pos1.y", Double.valueOf(((Location) this.pos1.get(id)).getY()));
					getPortalConfig().set(args[1] + ".pos1.z", Double.valueOf(((Location) this.pos1.get(id)).getZ()));
					getPortalConfig().set(args[1] + ".pos2.world", ((Location) this.pos2.get(id)).getWorld().getName());
					getPortalConfig().set(args[1] + ".pos2.x", Double.valueOf(((Location) this.pos2.get(id)).getX()));
					getPortalConfig().set(args[1] + ".pos2.y", Double.valueOf(((Location) this.pos2.get(id)).getY()));
					getPortalConfig().set(args[1] + ".pos2.z", Double.valueOf(((Location) this.pos2.get(id)).getZ()));
					getPortalConfig().set(args[1] + ".pos3.world", p.getLocation().getWorld().getName());
					getPortalConfig().set(args[1] + ".pos3.x", Double.valueOf(p.getLocation().getX()));
					getPortalConfig().set(args[1] + ".pos3.y", Double.valueOf(p.getLocation().getY()));
					getPortalConfig().set(args[1] + ".pos3.z", Double.valueOf(p.getLocation().getZ()));
					getPortalConfig().set(args[1] + ".pos3.pitch", Float.valueOf(p.getLocation().getPitch()));
					getPortalConfig().set(args[1] + ".pos3.yaw", Float.valueOf(p.getLocation().getYaw()));
					savePortals();
					sender.sendMessage(
							"§1[§6Blobcatraz§1]§r Created a teleporting portal called §2§l" + args[1] + "§a!");
					return true;
				} else if (args[0].equalsIgnoreCase("list")) 
				{
					if (this.portals.isEmpty()) 
					{
						sender.sendMessage(
								"§1[§6Blobcatraz§1]§r You have no portals! Do /portal create to make portals.");
						return true;
					}
					StringBuilder list = new StringBuilder();
					list.append("§1[§6Blobcatraz§1]§r List of portals: ");
					for (int x = 0; x < this.portals.size() - 1; x++) 
					{
						list.append("§2§l" + (String) this.portals.get(x) + "§a, ");
					}
					list.append("§2§l" + (String) this.portals.get(this.portals.size() - 1) + "§a.");
					sender.sendMessage(list.toString());
					return true;
				}
				else if (args[0].equalsIgnoreCase("remove")) 
				{
					if (!this.portals.contains(args[1])) 
					{
						sender.sendMessage(
								"§1[§6Blobcatraz§1]§r That portal does not exist! Do /portal list to see existing portals.");
						return true;
					}
					getPortalConfig().set(args[1], null);
					savePortals();
					sender.sendMessage("§1[§6Blobcatraz§1]§r Removed a portal called §2§l" + args[1] + "§a!");
					return true;
				}
			}
		}

		catch (Exception e) 
		{
			sender.sendMessage("§1[§6Blobcatraz§1]§r Something went wrong! Do /help Blobcatraz for more info.");
			return true;
		}

		return false;
	}

	private void deleteOld(Hologram h) 
	{
		h.delete();
	}

	public static String getFinalArg(String[] args, int start) 
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) 
		{
			if (i != start) 
			{
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		ItemStack gold_hoe = new ItemStack(Material.GOLD_HOE);
		ItemMeta gold_hoe_meta = gold_hoe.getItemMeta();
		gold_hoe_meta.setDisplayName("§3Blobcatraz §cPortal §cWand");
		gold_hoe_meta.setLore(Arrays.asList("Left Click to set Position 1", "Right Click to set Position 2"));
		gold_hoe.setItemMeta(gold_hoe_meta);

		if (!e.getPlayer().hasPermission("blobcatraz.wand.use")) 
		{
			return;
		}
		if (!e.getPlayer().getItemInHand().equals(gold_hoe)) 
		{
			return;
		}
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
		{
			this.pos1.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation());

			Location l = e.getClickedBlock().getLocation();
			int x1 = l.getBlockX();
			int y1 = l.getBlockY();
			int z1 = l.getBlockZ();

			e.getPlayer().sendMessage("§1[§6Blobcatraz§1]§r Position 1 set!");
		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
		{
			this.pos2.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation());

			Location l = e.getClickedBlock().getLocation();
			int x2 = l.getBlockX();
			int y2 = l.getBlockY();
			int z2 = l.getBlockZ();

			e.getPlayer().sendMessage("§1[§6Blobcatraz§1]§r Position 2 set!");
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		Location l = p.getLocation();
		for (String x : this.portals) 
		{
			Double x1 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos1.x")));
			Double y1 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos1.y")));
			Double z1 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos1.z")));
			Double x2 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos2.x")));
			Double y2 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos2.y")));
			Double z2 = Double.valueOf(Double.parseDouble(getPortalConfig().getString(x + ".pos2.z")));
			if (l.getWorld().getName().equals(getPortalConfig().getString(x + ".pos1.world"))) 
			{
				if (l.getWorld().getName().equals(getPortalConfig().getString(x + ".pos2.world"))) 
				{
					if ((l.getX() > Math.min(x1.doubleValue(), x2.doubleValue()))
							&& (l.getX() < Math.max(x1.doubleValue(), x2.doubleValue()) + 1.0D)
							&& (l.getY() >= Math.min(y1.doubleValue(), y2.doubleValue()))
							&& (l.getY() <= Math.max(y1.doubleValue(), y2.doubleValue()))
							&& (l.getZ() > Math.min(z1.doubleValue(), z2.doubleValue()))
							&& (l.getZ() < Math.max(z1.doubleValue(), z2.doubleValue()) + 1.0D)) 
					{
						if (getPortalConfig().contains(x + ".command")) 
						{
							if (!this.inPortal.contains(p.getUniqueId())) 
							{
								p.chat(getPortalConfig().getString(x + ".command"));
								this.inPortal.add(p.getUniqueId());
							}
							return;
						}
						Location pos3 = new Location(null, 0.0D, 0.0D, 0.0D);
						pos3.setWorld(Bukkit.getServer().getWorld(getPortalConfig().getString(x + ".pos3.world")));
						pos3.setX(Double.parseDouble(getPortalConfig().getString(x + ".pos3.x")));
						pos3.setY(Double.parseDouble(getPortalConfig().getString(x + ".pos3.y")));
						pos3.setZ(Double.parseDouble(getPortalConfig().getString(x + ".pos3.z")));
						pos3.setPitch(Float.parseFloat(getPortalConfig().getString(x + ".pos3.pitch")));
						pos3.setYaw(Float.parseFloat(getPortalConfig().getString(x + ".pos3.yaw")));
						p.teleport(pos3);
					} 
					else if (this.inPortal.contains(p.getUniqueId())) 
					{
						this.inPortal.remove(p.getUniqueId());
					}
				}
			}
		}
	}

	// Custom Portal Config After This Point
	private FileConfiguration portalConfig = null;
	private File portalConfigFile = null;

	public void reloadPortalConfig() 
	{
		if (portalConfigFile == null) 
		{
			portalConfigFile = new File(getDataFolder(), "portals.yml");
		}
		portalConfig = YamlConfiguration.loadConfiguration(portalConfigFile);

		final InputStream defPortalConfigStream = getResource("portals.yml");
		if (defPortalConfigStream != null) 
		{
			YamlConfiguration defPortalConfig = YamlConfiguration.loadConfiguration(defPortalConfigStream);
			portalConfig.setDefaults(defPortalConfig);
		}
	}

	public FileConfiguration getPortalConfig() 
	{
		if (portalConfig == null) 
		{
			reloadPortalConfig();
		}
		return portalConfig;
	}

	public void savePortalConfig() {
		if (portalConfig == null || portalConfigFile == null) 
		{
			return;
		}
		try 
		{
			getPortalConfig().save(portalConfigFile);
		} catch (IOException ex) 
		{
			getLogger().log(Level.SEVERE, "[Blobcatraz] Could not save portals to " + portalConfigFile, ex);
		}
	}
}