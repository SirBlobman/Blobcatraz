package com.SirBlobman.blobcatraz.world.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import com.SirBlobman.blobcatraz.Util;

@SuppressWarnings("deprecation")
public class FlatChunkGenerator extends ChunkGenerator
{
	private short[] layer;
	private byte[] layerDataValues;
	
	public FlatChunkGenerator()
	{
		this("64,stone");
	}
	
	public FlatChunkGenerator(String id)
	{
		if(id != null)
		{
			try
			{
				int y = 0;
				
				layer = new short['?'];
				layerDataValues = null;
				
				if(id.length() > 0 && id.charAt(0) == '.') {id = id.substring(1);}
				else {layer[(y++)] = (short) Material.BEDROCK.getId();}
				
				if(id.length() > 0)
				{
					String[] tokens = id.split("[,]");
					
					if(tokens.length % 2 != 0) throw new Exception();
					
					for(int i = 0; i < tokens.length; i += 2)
					{
						int height = Integer.parseInt(tokens[i]);
						
						if(height <= 0)
						{
							Util.print("Invalid height '" + tokens[i] + "'. Using 64 instead.");
							height = 64;
						}
						
						String[] materialTokens = tokens[(i + 1)].split("[:]", 2);
						byte dataValue = 0;
						
						if(materialTokens.length == 2)
						{
							try{dataValue = Byte.parseByte(materialTokens[1]);}
							catch (Exception ex) {Util.print("Invalid Data Value '" + materialTokens[1] + "'. Defaulting to 0."); dataValue = 0;}
						}
						
						Material mat = Material.matchMaterial(materialTokens[0]);
						if(mat == null)
						{
							try{mat = Material.getMaterial(Integer.parseInt(materialTokens[0]));}
							catch(Exception ex) {}
							
							if(mat == null)
							{
								Util.print("Invalid Block ID '" + materialTokens[0] + "'. Defaulting to Stone");
								mat = Material.STONE;
							}
						}
						if(!mat.isBlock())
						{
							Util.print("Invalid Block ID '" + materialTokens[0] + "'. Defaulting to Stone");
							mat = Material.STONE;
						}
						
						if(y + height > layer.length)
						{
							short[] newLayer = new short[Math.max(y + height, layer.length * 2)];
							System.arraycopy(layer, 0, newLayer, 0, y);
							
							layer = newLayer;
							if(layerDataValues != null)
							{
								byte[] newLayerDataValues = new byte[Math.max(y + height, this.layerDataValues.length * 2)];
								System.arraycopy(layerDataValues, 0, newLayerDataValues, 0, y);
								layerDataValues = newLayerDataValues;
							}
						}
						
						Arrays.fill(layer, y, y + height, (short) mat.getId());
						if(dataValue != 0)
						{
							if(layerDataValues == null) layerDataValues = new byte[layer.length];
							
							Arrays.fill(layerDataValues, y, y + height, dataValue);
						}
						
						y += height;
					}
				}
				if(layer.length > y)
				{
					short[] newLayer = new short[y];
					System.arraycopy(layer, 0, newLayer, 0, y);
					layer = newLayer;
				}
				if(layerDataValues != null && layerDataValues.length > y)
				{
					byte[] newLayerDataValues = new byte[y];
					System.arraycopy(layerDataValues, 0, newLayerDataValues, 0, y);
					layerDataValues = newLayerDataValues;
				}
			}
			catch (Exception ex)
			{
				Util.print("Error parsing ID '" + id + "'. Using 64 levels of stone: " + ex.toString());
				ex.printStackTrace();
				layerDataValues = null;
				layer = new short[65];
				layer[0] = (short) Material.BEDROCK.getId();
				Arrays.fill(layer, 1, 65, (short) Material.STONE.getId());
			}
		}
		else
		{
			layerDataValues = null;
			layer = new short[65];
			layer[0] = (short) Material.BEDROCK.getId();
			Arrays.fill(layer, 1, 65, (short) Material.STONE.getId());
		}
	}
	
	public short[][] generateExtBlockSections(World w, Random r, int x, int z, BiomeGrid biomes)
	{
		int maxHeight = w.getMaxHeight();
		if(layer.length > maxHeight)
		{
			Util.print("Error, chunk height " + layer.length + " is greater than the world height (" + maxHeight + "). Trimming to max height");
			short[] newLayer = new short[maxHeight];
			System.arraycopy(layer, 0, newLayer, 0, maxHeight);
			layer = newLayer;
		}
		
		short[][] result = new short[maxHeight / 16][];
		
		for(int i = 0; i < layer.length; i += 16)
		{
			result[(i >> 4)] = new short['?'];
			for(int y = 0; y < Math.min(16, layer.length - i); y++) Arrays.fill(result[(i >> 4)], y * 16 * 16, (y + 1) * 16 * 16, layer[(i + y)]);
		}
		
		return result;
	}
	
	public List<BlockPopulator> getDefaultPopulators(World w)
	{
		if(layerDataValues != null) return Arrays.asList(new BlockPopulator[] {new FlatBlockPopulator(layerDataValues)});
		
		return new ArrayList<BlockPopulator>();
	}
	
	public Location getFixedSpawnLocation(World w, Random r)
	{
		if(!w.isChunkLoaded(0, 0)) w.loadChunk(0, 0);
		
		if(w.getHighestBlockYAt(0,0) <= 0 && w.getBlockAt(0, 0, 0).getType() == Material.AIR) return new Location(w, 0, 64D, 0D);
		
		return new Location(w, 0D, w.getHighestBlockYAt(0, 0), 0D);
	}
}