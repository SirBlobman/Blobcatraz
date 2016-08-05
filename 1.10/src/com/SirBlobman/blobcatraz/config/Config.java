package com.SirBlobman.blobcatraz.config;

import java.io.File;

import com.SirBlobman.blobcatraz.Blobcatraz;

public interface Config 
{
	public static File configFolder = Blobcatraz.instance.getDataFolder();
	
	/**
	 * Save the configuration to a file
	 * @see File
	 */
	public static void save()
	{
		
	}
	
	/**
	 * Load the config from a file
	 * @see File
	 */
	public static void load()
	{
		
	}
	
	/**
	 * Write a default value to the config so that it doesn't return null
	 * @see File
	 */
	public static void writeDefault()
	{
		
	}
}