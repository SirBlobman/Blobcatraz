package com.SirBlobman.blobcatraz.utility;

import java.util.Set;
import java.util.UUID;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PlayerConnection;

public class PlayerUtil 
{
	/**
	 * This gets the owner of the server
	 * The owner of the server will be pinged every time a Player joins
	 * @return OfflinePlayer instance of the Owner
	 * @see #ping(Player)
	 * @see Bukkit#getOfflinePlayer(UUID)
	 */
	public static OfflinePlayer getOwner()
	{
		FileConfiguration fc = ConfigBlobcatraz.load();
		String id = fc.getString("owner");
		if(id == null) return null;
		UUID uuid = UUID.fromString(id);
		if(uuid == null) return null;
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	/**
	 * Send a EXP sound to a Player
	 * @param p Player to ping
	 * @see Sound
	 * @see Player
	 * @see Sound#ENTITY_EXPERIENCE_ORB_PICKUP
	 * @see Player#playSound(Location, Sound, float, float)
	 */
	public static void ping(Player p)
	{
		if(p == null) return;
		Location location = p.getLocation();
		Sound sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float volume = 1000.0F;
		float pitch = 1.0F;
		p.playSound(location, sound, volume, pitch);
	}
	
	/**
	 * Heal an entity<br>
	 * Removes potion effects, feeds them, and sets their health to the MAX the player can have
	 * @param le Entity to heal
	 * @see Player
	 * @see Player#setHealth(double)
	 */
	public static void heal(LivingEntity le)
	{
		le.setHealth(le.getMaxHealth());
		for(PotionEffect pe : le.getActivePotionEffects()) le.removePotionEffect(pe.getType());
		if(le instanceof Player) feed((Player) le);
	}
	
	/**
	 * Reset a player's food level to 20.0
	 * @param p Player to feed
	 * @see Player
	 * @see Player#setFoodLevel(int)
	 */
	public static void feed(Player p)
	{
		if(p == null) return;
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
	}
	
	/**
	 * Send a title to a Player
	 * @param p Player to send the title to
	 * @param title String of the title
	 * @param subtitle String of the subtitle
	 * @see String
	 * @see Player
	 * @see Player#sendTitle(String, String)
	 */
	@SuppressWarnings("deprecation")
	public static void sendTitle(Player p, String title, String subtitle)
	{
		if(p == null) return;
		p.sendTitle(Util.format(title), Util.format(subtitle));
	}
	
	/**
	 * Uses 1.10.R1 NMS code to send an Action Bar Message to a Player
	 * @param p Player to send the message to
	 * @param msg Action Bar message
	 * @see String
	 * @see Player
	 * @see #ping(Player)
	 * @see IChatBaseComponent
	 * @see ChatSerializer
	 * @see PacketPlayOutChat
	 * @see CraftPlayer
	 * @see EntityPlayer
	 * @see PlayerConnection
	 * @see PlayerConnection#sendPacket(net.minecraft.server.v1_10_R1.Packet)
	 */
	public static void sendAction1_10(Player p, String msg)
	{
		if(p == null) return;
		try
		{
			ping(p);
			IChatBaseComponent message = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
			PacketPlayOutChat action = new PacketPlayOutChat(message, (byte) 2);
			CraftPlayer cp = (CraftPlayer) p;
			EntityPlayer ep = cp.getHandle();
			PlayerConnection pc = ep.playerConnection;
			pc.sendPacket(action);
		} catch(Exception ex)
		{
			Util.print("Could not send action to " + p.getName() + ": " + ex.getCause());
		}
	}
	
	/**
	 * Gets the Block that a Player is looking at
	 * @param p Player to check
	 * @return Block the Player is looking at
	 * @see Block
	 * @see Player
	 * @see Player#getTargetBlock(Set, int)
	 */
	public static Block getPlayerLook(Player p)
	{
		if(p == null) return null;
		Set<Material> none = null;
		int range = 200;
		Block b = p.getTargetBlock(none, range);
		return b;
	}
	
	/**
	 * Get the location that a player is looking at
	 * @param p Player to check
	 * @return Location of the block the player is looking at (max 200 blocks)
	 * @see Location
	 * @see Player
	 * @see #getPlayerLook(Player)
	 */
	public static Location getPlayerLookLocation(Player p)
	{
		if(p == null) return null;
		Block b = getPlayerLook(p);
		Location look = b.getLocation();
		return look;
	}
	
	/**
	 * Checks if the command sender has a permission
	 * @param cs CommandSender to check
	 * @param permission Permission to check
	 * @return <b>true</b> if the CommandSender is console, has OP, or has the permission<br><b>false</b> if the CommandSender doesn't have permission
	 * @see String
	 * @see Boolean
	 * @see CommandSender
	 * @see CommandSender#hasPermission(String)
	 */
	public static boolean hasPermission(CommandSender cs, String permission)
	{
		ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
		if(cs != ccs)
		{
			if(cs.isOp() || cs.hasPermission(permission)) return true;
			noPermission(cs, permission);
			return false;
		}
		return true;
	}
	
	/**
	 * Used to send a no perms message to a {@link CommandSender}
	 * @param cs CommandSender which will recieve the message
	 * @param permission Permission which is required that they don't have
	 * @see String
	 * @see CommandSender
	 * @see CommandSender#sendMessage(String)
	 */
	public static void noPermission(CommandSender cs, String permission)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String title = Util.message("title.noPermission");
			String subtitle = Util.message("subtitle.noPermission", permission);
			sendTitle(p, title, subtitle);
			return;
		}
		else
		{
			cs.sendMessage(Util.noPerms(permission));
			return;
		}
	}
	
	/**
	 * Used in onDisable() to close the inventory of every player on the server
	 * @see com.SirBlobman.blobcatraz.Blobcatraz#onDisable()
	 * @see Bukkit#getOnlinePlayers()
	 * @see Player#closeInventory()
	 */
	public static void closeAllInventories()
	{
		for(Player p : Bukkit.getOnlinePlayers()) p.closeInventory();
	}
	
	/**
	 * Open a GUI for a Player
	 * @param p Player
	 * @param i Inventory (GUI) to open
	 * @see Player#openInventory(Inventory)
	 */
	public static void open(Player p, Inventory i)
	{
		p.openInventory(i);
	}
	
	/**
	 * Clear the inventory of a Player
	 * <br>Saves the armor and Off-Hand item
	 * @param p Player to clear
	 * @see PlayerInventory#clear()
	 */
	public static void clear(Player p)
	{
		PlayerInventory pi = p.getInventory();
		ItemStack[] armor = pi.getArmorContents().clone();
		ItemStack off = pi.getItemInOffHand().clone();
		pi.clear();
		pi.setArmorContents(armor);
		pi.setItemInOffHand(off);
		p.sendMessage(Util.blobcatraz + "Your inventory was cleared!");
		p.sendMessage("Your armor was saved!");
	}
	
	/**
	 * Removes the armor and shield from a Player
	 * @param p Player to remove the armor of
	 */
	public static void clearArmor(Player p)
	{
		PlayerInventory pi = p.getInventory();
		ItemStack air = new ItemStack(Material.AIR);
		ItemStack[] armor = new ItemStack[] {air, air, air, air};
		pi.setArmorContents(armor);
		pi.setItemInOffHand(air);
		p.sendMessage(Util.blobcatraz + "Your armor and shield were removed");
	}
	
	/**
	 * Remove all items from the enderchest of a Player
	 * @param p Player to clear
	 */
	public static void clearEnderChest(Player p)
	{
		Inventory echest = p.getEnderChest();
		echest.clear();
		p.sendMessage(Util.blobcatraz + "Your enderchest is now empty!");
	}
}