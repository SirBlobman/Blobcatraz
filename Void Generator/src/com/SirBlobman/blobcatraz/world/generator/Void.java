package com.SirBlobman.blobcatraz.world.generator;

import org.bukkit.generator.ChunkGenerator;

import com.SirBlobman.blobcatraz.Blobcatraz;

public class Void extends Blobcatraz 
{
	@Override
	public void onEnable()
	{
		System.out.println("VoidGenerator has been enabled!");
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("VoidGenerator has been disabled!");
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String world, String uuid)
	{
		return new VoidGenerator();
	}
}