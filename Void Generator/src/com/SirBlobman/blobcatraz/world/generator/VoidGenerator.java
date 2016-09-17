package com.SirBlobman.blobcatraz.world.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class VoidGenerator extends ChunkGenerator 
{
	public List<BlockPopulator> getDefaultPopulators(World w) {return Arrays.asList(new BlockPopulator[0]);}
	public boolean canSpawn(World w, int x, int z) {return true;}
	public int coordToByte(int x, int y, int z) {return (x * 16 + z) * 128 + y;}
	
	public byte[] generate(World w, Random r, int cx, int cz)
	{
		byte[] result = new byte[32767];
		if(cx == 0 && cz == 0)
		{
			@SuppressWarnings("deprecation")
			byte bedrock = (byte) Material.BEDROCK.getId();
			result[coordToByte(0, 64, 0)] = bedrock;
		}
		return result;
	}
	
	public Location getFixedSpawnLocation(World w, Random r)
	{
		return new Location(w, 0.5D, 66, 0.5D);
	}
}