package com.SirBlobman.blobcatraz.world.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class FlatBlockPopulator extends BlockPopulator 
{
	byte[] layerDataValues;
	
	protected FlatBlockPopulator(byte[] layerDataValues)
	{
		this.layerDataValues = layerDataValues;
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World w, Random r, Chunk c)
	{
		if(layerDataValues != null)
		{
			int x = c.getX() << 4;
			int z = c.getZ() << 4;
			
			for(int y = 0; y < layerDataValues.length; y++)
			{
				byte dataValue = layerDataValues[y];
				
				if(dataValue != 0)
				{
					for(int xx = 0; xx < 16; xx++)
					{
						for(int zz = 0; zz < 16; zz++)
						{
							w.getBlockAt(x + xx, y, z + zz).setData(dataValue);
						}
					}
				}
			}
		}
	}
}